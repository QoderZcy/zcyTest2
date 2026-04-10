import { useState, useCallback } from 'react'
import FileUploadZone from './components/FileUploadZone'
import FileList from './components/FileList'
import DialogDemo from './components/DialogDemo'
import Toast from './components/Toast'

export interface FileInfo {
  name: string
  size: number
  uploadTime: string
}

export interface ToastMessage {
  id: string
  type: 'success' | 'error' | 'info'
  message: string
}

function App() {
  const [files, setFiles] = useState<FileInfo[]>([])
  const [toasts, setToasts] = useState<ToastMessage[]>([])

  const showToast = useCallback((type: ToastMessage['type'], message: string) => {
    const id = Date.now().toString()
    setToasts(prev => [...prev, { id, type, message }])
    setTimeout(() => {
      setToasts(prev => prev.filter(t => t.id !== id))
    }, 3000)
  }, [])

  const handleUploadSuccess = useCallback((fileInfo: FileInfo) => {
    setFiles(prev => [fileInfo, ...prev])
    showToast('success', `文件 "${fileInfo.name}" 上传成功！`)
  }, [showToast])

  const handleDeleteSuccess = useCallback((filename: string) => {
    setFiles(prev => prev.filter(f => f.name !== filename))
    showToast('success', `文件 "${filename}" 已删除`)
  }, [showToast])

  const handleError = useCallback((message: string) => {
    showToast('error', message)
  }, [showToast])

  return (
    <div className="min-h-screen py-8 px-4">
      <div className="max-w-4xl mx-auto">
        <h1 className="text-4xl font-bold text-white text-center mb-8">
          文件上传下载系统
        </h1>
        
        <DialogDemo />
        
        <FileUploadZone 
          onUploadSuccess={handleUploadSuccess}
          onError={handleError}
        />
        
        <FileList 
          files={files}
          onDeleteSuccess={handleDeleteSuccess}
          onError={handleError}
        />
      </div>
      
      <div className="fixed top-4 right-4 space-y-2 z-50">
        {toasts.map(toast => (
          <Toast key={toast.id} {...toast} />
        ))}
      </div>
    </div>
  )
}

export default App
