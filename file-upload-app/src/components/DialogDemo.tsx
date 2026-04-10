import { MessageSquare, AlertTriangle, HelpCircle } from 'lucide-react'

export default function DialogDemo() {
  const handleAlert = () => {
    alert('这是一个警告对话框！\n\n用于向用户显示重要信息。')
  }

  const handleConfirm = () => {
    const result = confirm('您确定要执行此操作吗？\n\n点击"确定"继续，点击"取消"返回。')
    if (result) {
      alert('您点击了"确定"')
    } else {
      alert('您点击了"取消"')
    }
  }

  const handlePrompt = () => {
    const result = prompt('请输入您的姓名：', '张三')
    if (result !== null) {
      alert(`您好，${result}！欢迎访问文件上传系统。`)
    } else {
      alert('您取消了输入')
    }
  }

  return (
    <div className="bg-white rounded-xl shadow-lg p-6 mb-6">
      <h2 className="text-xl font-bold text-gray-800 mb-4 flex items-center gap-2">
        <MessageSquare className="w-6 h-6 text-purple-600" />
        对话框测试区域
      </h2>
      <p className="text-gray-600 mb-4">点击下方按钮测试不同类型的浏览器对话框：</p>
      <div className="flex flex-wrap gap-3">
        <button
          onClick={handleAlert}
          className="flex items-center gap-2 px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
        >
          <AlertTriangle className="w-4 h-4" />
          测试 Alert 对话框
        </button>
        <button
          onClick={handleConfirm}
          className="flex items-center gap-2 px-4 py-2 bg-amber-500 text-white rounded-lg hover:bg-amber-600 transition-colors"
        >
          <HelpCircle className="w-4 h-4" />
          测试 Confirm 对话框
        </button>
        <button
          onClick={handlePrompt}
          className="flex items-center gap-2 px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition-colors"
        >
          <MessageSquare className="w-4 h-4" />
          测试 Prompt 对话框
        </button>
      </div>
    </div>
  )
}
