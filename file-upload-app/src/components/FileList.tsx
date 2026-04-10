import { useState, useEffect } from 'react'
import { FileText, Download, Trash2, Loader2, FolderOpen } from 'lucide-react'
import axios from 'axios'
import type { FileInfo } from '../App'

interface FileListProps {
  files: FileInfo[]
  onDeleteSuccess: (filename: string) => void
  onError: (message: string) => void
}

export default function FileList({ files, onDeleteSuccess, onError }: FileListProps) {
  const [serverFiles, setServerFiles] = useState<FileInfo[]>([])
  const [loading, setLoading] = useState(false)
  const [deletingFile, setDeletingFile] = useState<string | null>(null)

  const fetchFiles = async () => {
    try {
      const response = await axios.get('/api/files')
      if (response.data.success) {
        setServerFiles(response.data.files)
      }
    } catch (error) {
      console.error('Failed to fetch files:', error)
    }
  }

  useEffect(() => {
    fetchFiles()
  }, [files])

  const handleDownload = async (filename: string) => {
    try {
      const response = await axios.get(`/api/download/${encodeURIComponent(filename)}`, {
        responseType: 'blob'
      })
      
      const url = window.URL.createObjectURL(new Blob([response.data]))
      const link = document.createElement('a')
      link.href = url
      link.setAttribute('download', filename)
      document.body.appendChild(link)
      link.click()
      link.remove()
      window.URL.revokeObjectURL(url)
    } catch (error) {
      onError('文件下载失败')
      console.error('Download error:', error)
    }
  }

  const handleDelete = async (filename: string) => {
    const confirmed = confirm(`确定要删除文件 "${filename}" 吗？\n\n此操作不可撤销。`)
    if (!confirmed) return

    setDeletingFile(filename)
    try {
      const response = await axios.delete(`/api/files/${encodeURIComponent(filename)}`)
      if (response.data.success) {
        onDeleteSuccess(filename)
        fetchFiles()
      }
    } catch (error) {
      onError('文件删除失败')
      console.error('Delete error:', error)
    } finally {
      setDeletingFile(null)
    }
  }

  const formatFileSize = (bytes: number) => {
    if (bytes === 0) return '0 Bytes'
    const k = 1024
    const sizes = ['Bytes', 'KB', 'MB', 'GB']
    const i = Math.floor(Math.log(bytes) / Math.log(k))
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i]
  }

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleString('zh-CN')
  }

  const allFiles = [...files, ...serverFiles.filter(sf => !files.some(f => f.name === sf.name))]

  return (
    <div className="bg-white rounded-xl shadow-lg p-6">
      <div className="flex items-center justify-between mb-4">
        <h2 className="text-xl font-bold text-gray-800 flex items-center gap-2">
          <FolderOpen className="w-6 h-6 text-purple-600" />
          文件列表
        </h2>
        <span className="text-sm text-gray-500">
          共 {allFiles.length} 个文件
        </span>
      </div>

      {allFiles.length === 0 ? (
        <div className="text-center py-12 text-gray-400">
          <FileText className="w-16 h-16 mx-auto mb-4 opacity-50" />
          <p className="text-lg">暂无文件</p>
          <p className="text-sm mt-2">上传的文件将显示在这里</p>
        </div>
      ) : (
        <div className="space-y-3">
          {allFiles.map((file) => (
            <div
              key={file.name}
              className="flex items-center justify-between p-4 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors"
            >
              <div className="flex items-center gap-4 flex-1 min-w-0">
                <div className="p-2 bg-purple-100 rounded-lg">
                  <FileText className="w-6 h-6 text-purple-600" />
                </div>
                <div className="min-w-0 flex-1">
                  <p className="font-medium text-gray-800 truncate" title={file.name}>
                    {file.name}
                  </p>
                  <p className="text-sm text-gray-500">
                    {formatFileSize(file.size)} · {formatDate(file.uploadTime)}
                  </p>
                </div>
              </div>

              <div className="flex items-center gap-2 ml-4">
                <button
                  onClick={() => handleDownload(file.name)}
                  className="p-2 text-blue-600 hover:bg-blue-50 rounded-lg transition-colors"
                  title="下载"
                >
                  <Download className="w-5 h-5" />
                </button>
                <button
                  onClick={() => handleDelete(file.name)}
                  disabled={deletingFile === file.name}
                  className="p-2 text-red-600 hover:bg-red-50 rounded-lg transition-colors disabled:opacity-50"
                  title="删除"
                >
                  {deletingFile === file.name ? (
                    <Loader2 className="w-5 h-5 animate-spin" />
                  ) : (
                    <Trash2 className="w-5 h-5" />
                  )}
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}
