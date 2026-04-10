import { CheckCircle, XCircle, Info } from 'lucide-react'

interface ToastProps {
  type: 'success' | 'error' | 'info'
  message: string
}

export default function Toast({ type, message }: ToastProps) {
  const icons = {
    success: <CheckCircle className="w-5 h-5 text-green-500" />,
    error: <XCircle className="w-5 h-5 text-red-500" />,
    info: <Info className="w-5 h-5 text-blue-500" />
  }

  const bgColors = {
    success: 'bg-green-50 border-green-200',
    error: 'bg-red-50 border-red-200',
    info: 'bg-blue-50 border-blue-200'
  }

  return (
    <div className={`flex items-center gap-3 px-4 py-3 rounded-lg border shadow-lg ${bgColors[type]}`}>
      {icons[type]}
      <span className="text-gray-800 font-medium">{message}</span>
    </div>
  )
}
