import express from 'express'
import cors from 'cors'
import multer from 'multer'
import path from 'path'
import fs from 'fs'
import { fileURLToPath } from 'url'

const __filename = fileURLToPath(import.meta.url)
const __dirname = path.dirname(__filename)

const app = express()
const PORT = 3001

// 中间件
app.use(cors())
app.use(express.json())

// 确保上传目录存在
const uploadsDir = path.join(__dirname, 'uploads')
if (!fs.existsSync(uploadsDir)) {
  fs.mkdirSync(uploadsDir, { recursive: true })
}

// 配置 multer 存储
const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    cb(null, uploadsDir)
  },
  filename: (req, file, cb) => {
    // 保留原始文件名，添加时间戳避免冲突
    const timestamp = Date.now()
    const safeFilename = `${timestamp}_${file.originalname}`
    cb(null, safeFilename)
  }
})

const upload = multer({
  storage: storage,
  limits: {
    fileSize: 100 * 1024 * 1024 // 100MB
  }
})

// 文件上传接口
app.post('/api/upload', upload.single('file'), (req, res) => {
  try {
    if (!req.file) {
      return res.status(400).json({
        success: false,
        message: '没有上传文件'
      })
    }

    const fileInfo = {
      name: req.file.filename,
      originalName: req.file.originalname,
      size: req.file.size,
      uploadTime: new Date().toISOString()
    }

    res.json({
      success: true,
      message: '文件上传成功',
      file: fileInfo
    })
  } catch (error) {
    console.error('Upload error:', error)
    res.status(500).json({
      success: false,
      message: '文件上传失败'
    })
  }
})

// 获取文件列表接口
app.get('/api/files', (req, res) => {
  try {
    const files = fs.readdirSync(uploadsDir)
    const fileList = files
      .filter(filename => !filename.startsWith('.'))
      .map(filename => {
        const filePath = path.join(uploadsDir, filename)
        const stats = fs.statSync(filePath)
        return {
          name: filename,
          size: stats.size,
          uploadTime: stats.mtime.toISOString()
        }
      })
      .sort((a, b) => new Date(b.uploadTime) - new Date(a.uploadTime))

    res.json({
      success: true,
      files: fileList
    })
  } catch (error) {
    console.error('List files error:', error)
    res.status(500).json({
      success: false,
      message: '获取文件列表失败'
    })
  }
})

// 文件下载接口
app.get('/api/download/:filename', (req, res) => {
  try {
    const filename = decodeURIComponent(req.params.filename)
    const filePath = path.join(uploadsDir, filename)

    // 安全检查：确保文件在 uploads 目录内
    const resolvedPath = path.resolve(filePath)
    const resolvedUploadsDir = path.resolve(uploadsDir)
    if (!resolvedPath.startsWith(resolvedUploadsDir)) {
      return res.status(403).json({
        success: false,
        message: '访问被拒绝'
      })
    }

    if (!fs.existsSync(filePath)) {
      return res.status(404).json({
        success: false,
        message: '文件不存在'
      })
    }

    res.download(filePath, filename)
  } catch (error) {
    console.error('Download error:', error)
    res.status(500).json({
      success: false,
      message: '文件下载失败'
    })
  }
})

// 文件删除接口
app.delete('/api/files/:filename', (req, res) => {
  try {
    const filename = decodeURIComponent(req.params.filename)
    const filePath = path.join(uploadsDir, filename)

    // 安全检查
    const resolvedPath = path.resolve(filePath)
    const resolvedUploadsDir = path.resolve(uploadsDir)
    if (!resolvedPath.startsWith(resolvedUploadsDir)) {
      return res.status(403).json({
        success: false,
        message: '访问被拒绝'
      })
    }

    if (!fs.existsSync(filePath)) {
      return res.status(404).json({
        success: false,
        message: '文件不存在'
      })
    }

    fs.unlinkSync(filePath)
    res.json({
      success: true,
      message: '文件删除成功'
    })
  } catch (error) {
    console.error('Delete error:', error)
    res.status(500).json({
      success: false,
      message: '文件删除失败'
    })
  }
})

// 健康检查接口
app.get('/api/health', (req, res) => {
  res.json({
    success: true,
    message: '服务器运行正常'
  })
})

app.listen(PORT, () => {
  console.log(`文件上传服务器运行在 http://localhost:${PORT}`)
  console.log(`上传目录: ${uploadsDir}`)
})
