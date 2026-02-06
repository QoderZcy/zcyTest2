# API参考

<cite>
**本文档中引用的文件**
- [API_DOCUMENTATION.md](file://API_DOCUMENTATION.md)
- [PhotoController.java](file://src/main/java/com/photo/controller/PhotoController.java)
- [AuthController.java](file://src/main/java/com/photo/controller/AuthController.java)
- [NoteController.java](file://src/main/java/com/photo/controller/NoteController.java)
- [BlogController.java](file://src/main/java/com/photo/controller/BlogController.java)
- [ApiResponse.java](file://src/main/java/com/photo/dto/ApiResponse.java)
- [PhotoDTO.java](file://src/main/java/com/photo/dto/PhotoDTO.java)
- [PhotoUploadResponse.java](file://src/main/java/com/photo/dto/PhotoUploadResponse.java)
- [StorageInfo.java](file://src/main/java/com/photo/dto/StorageInfo.java)
- [NoteDTO.java](file://src/main/java/com/photo/dto/NoteDTO.java)
- [BlogDTO.java](file://src/main/java/com/photo/dto/BlogDTO.java)
- [PhotoService.java](file://src/main/java/com/photo/service/PhotoService.java)
- [NoteService.java](file://src/main/java/com/photo/service/NoteService.java)
- [BlogService.java](file://src/main/java/com/photo/service/BlogService.java)
- [FileStorageService.java](file://src/main/java/com/photo/service/FileStorageService.java)
- [SecurityUtils.java](file://src/main/java/com/photo/util/SecurityUtils.java)
- [SecurityConfig.java](file://src/main/java/com/photo/config/SecurityConfig.java)
- [application.yml](file://src/main/resources/application.yml)
- [FileStorageProperties.java](file://src/main/java/com/photo/config/FileStorageProperties.java)
- [GlobalExceptionHandler.java](file://src/main/java/com/photo/exception/GlobalExceptionHandler.java)
- [OpenApiConfig.java](file://src/main/java/com/photo/config/OpenApiConfig.java)
- [schema.sql](file://src/main/resources/schema.sql)
- [login.html](file://src/main/resources/templates/login.html)
- [dashboard.html](file://src/main/resources/templates/dashboard.html)
- [Note.java](file://src/main/java/com/photo/entity/Note.java)
- [Blog.java](file://src/main/java/com/photo/entity/Blog.java)
- [NoteRepository.java](file://src/main/java/com/photo/repository/NoteRepository.java)
- [BlogRepository.java](file://src/main/java/com/photo/repository/BlogRepository.java)
</cite>

## 更新摘要
**所做更改**
- 新增认证相关API接口（登录、注册、注销）
- 新增便签管理API接口（CRUD操作、Markdown预览）
- 新增博客管理API接口（CRUD操作、分类筛选）
- 扩展统一响应格式说明
- 更新安全配置和权限控制说明
- 新增数据库实体和仓库层说明

## 目录
1. [简介](#简介)
2. [基础信息](#基础信息)
3. [统一响应格式](#统一响应格式)
4. [错误码说明](#错误码说明)
5. [API接口详情](#api接口详情)
6. [安全说明](#安全说明)
7. [性能优化](#性能优化)
8. [数据库设计](#数据库设计)
9. [Swagger文档](#swagger文档)

## 简介

本文档详细介绍了基于Spring Boot构建的综合内容管理系统，包括照片上传下载、用户认证、便签管理和博客系统在内的完整RESTful API接口。该系统提供了完整的多媒体内容管理功能，包括照片管理、用户认证、便签编辑和博客发布等操作，并具备完善的错误处理和安全机制。

## 基础信息

- **Base URL**: `http://localhost:8080/api`
- **API版本**: v1.0.0
- **认证方式**: 支持表单认证（可扩展JWT认证）
- **内容类型**: `application/json`（除文件上传接口外）

## 统一响应格式

所有API响应均采用统一格式：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {...},
  "timestamp": 1234567890
}
```

### 响应字段说明

| 字段名 | 类型 | 说明 |
|--------|------|------|
| code | Integer | 响应码，200表示成功 |
| message | String | 响应消息 |
| data | Object | 响应数据，可能为null |
| timestamp | Long | 时间戳 |

**章节来源**
- [ApiResponse.java](file://src/main/java/com/photo/dto/ApiResponse.java#L10-L63)

## 错误码说明

| 错误码 | 说明 | 示例消息 |
|--------|------|----------|
| 400 | 请求参数错误 | "用户名长度必须在3-50个字符之间" |
| 400 | 文件大小超限 | "文件大小不能超过 10.00 MB" |
| 401 | 未授权访问 | "用户名或密码错误" |
| 403 | 访问被拒绝 | "无权删除该照片" |
| 403 | 非法访问来源 | "非法访问来源" |
| 404 | 资源不存在 | "照片不存在: 123" |
| 500 | 服务器内部错误 | "文件存储失败" |
| 507 | 存储空间不足 | "存储空间不足" |

**章节来源**
- [GlobalExceptionHandler.java](file://src/main/java/com/photo/exception/GlobalExceptionHandler.java#L24-L138)

## API接口详情

### 认证相关API

#### 1. 用户登录

**接口地址**: `POST /auth/login`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| username | String | 是 | 用户名 |
| password | String | 是 | 密码 |

**成功响应示例**:
```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "message": "登录成功",
    "username": "admin",
    "redirectUrl": "/"
  },
  "timestamp": 1704110400000
}
```

**cURL示例**:
```bash
curl -X POST http://localhost:8080/auth/login \
  -F "username=admin" \
  -F "password=123456"
```

#### 2. 用户注册

**接口地址**: `POST /auth/register`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| username | String | 是 | 用户名（3-50字符） |
| password | String | 是 | 密码（至少6字符） |
| email | String | 否 | 邮箱地址 |

**成功响应示例**:
```json
{
  "code": 200,
  "message": "注册成功",
  "data": "注册成功",
  "timestamp": 1704110400000
}
```

**cURL示例**:
```bash
curl -X POST http://localhost:8080/auth/register \
  -F "username=testuser" \
  -F "password=password123" \
  -F "email=test@example.com"
```

#### 3. 用户注销

**接口地址**: `POST /auth/logout`

**请求参数**: 无

**成功响应示例**:
```json
{
  "code": 200,
  "message": "注销成功",
  "data": "注销成功",
  "timestamp": 1704110400000
}
```

**章节来源**
- [AuthController.java](file://src/main/java/com/photo/controller/AuthController.java#L47-L90)

### 便签管理API

#### 4. 便签列表页面

**接口地址**: `GET /`

**请求参数**: 无

**响应**: HTML页面（Thymeleaf模板）

**章节来源**
- [NoteController.java](file://src/main/java/com/photo/controller/NoteController.java#L29-L35)

#### 5. 新建便签表单

**接口地址**: `GET /notes/new`

**请求参数**: 无

**响应**: HTML页面（Thymeleaf模板）

**章节来源**
- [NoteController.java](file://src/main/java/com/photo/controller/NoteController.java#L41-L47)

#### 6. 创建便签

**接口地址**: `POST /notes`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| title | String | 是 | 便签标题 |
| content | String | 是 | 便签内容（Markdown格式） |

**响应**: 重定向到便签详情页面

**章节来源**
- [NoteController.java](file://src/main/java/com/photo/controller/NoteController.java#L53-L68)

#### 7. 便签详情页面

**接口地址**: `GET /notes/{id}`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 便签ID |

**响应**: HTML页面（Thymeleaf模板）

**章节来源**
- [NoteController.java](file://src/main/java/com/photo/controller/NoteController.java#L74-L88)

#### 8. 编辑便签表单

**接口地址**: `GET /notes/{id}/edit`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 便签ID |

**响应**: HTML页面（Thymeleaf模板）

**章节来源**
- [NoteController.java](file://src/main/java/com/photo/controller/NoteController.java#L94-L109)

#### 9. 更新便签

**接口地址**: `POST /notes/{id}`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 便签ID |
| title | String | 是 | 便签标题 |
| content | String | 是 | 便签内容（Markdown格式） |

**响应**: 重定向到便签详情页面

**章节来源**
- [NoteController.java](file://src/main/java/com/photo/controller/NoteController.java#L115-L132)

#### 10. 删除便签

**接口地址**: `POST /notes/{id}/delete`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 便签ID |

**响应**: 重定向到便签列表页面

**章节来源**
- [NoteController.java](file://src/main/java/com/photo/controller/NoteController.java#L138-L149)

#### 11. Markdown预览API

**接口地址**: `POST /api/preview`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| markdown | String | 是 | Markdown文本 |

**响应**: 渲染后的HTML内容

**成功响应示例**:
```html
<h1>标题</h1>
<p>段落内容</p>
<ul>
  <li>列表项</li>
</ul>
```

**cURL示例**:
```bash
curl -X POST http://localhost:8080/api/preview \
  -H "Content-Type: application/json" \
  -d "# 标题\n\n段落内容"
```

**章节来源**
- [NoteController.java](file://src/main/java/com/photo/controller/NoteController.java#L155-L160)
- [NoteService.java](file://src/main/java/com/photo/service/NoteService.java#L120-L132)

### 博客管理API

#### 12. 博客列表页面

**接口地址**: `GET /blogs`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| category | String | 否 | 分类过滤 |
| author | String | 否 | 作者过滤 |

**响应**: HTML页面（Thymeleaf模板）

**章节来源**
- [BlogController.java](file://src/main/java/com/photo/controller/BlogController.java#L30-L51)

#### 13. 新建博客表单

**接口地址**: `GET /blogs/new`

**请求参数**: 无

**响应**: HTML页面（Thymeleaf模板）

**章节来源**
- [BlogController.java](file://src/main/java/com/photo/controller/BlogController.java#L57-L63)

#### 14. 创建博客

**接口地址**: `POST /blogs`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| title | String | 是 | 博客标题 |
| author | String | 是 | 博客作者 |
| content | String | 是 | 博客内容（Markdown格式） |
| category | String | 否 | 博客分类 |
| tags | String | 否 | 博客标签（逗号分隔） |

**响应**: 重定向到博客详情页面

**章节来源**
- [BlogController.java](file://src/main/java/com/photo/controller/BlogController.java#L69-L87)

#### 15. 博客详情页面

**接口地址**: `GET /blogs/{id}`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 博客ID |

**响应**: HTML页面（Thymeleaf模板）

**章节来源**
- [BlogController.java](file://src/main/java/com/photo/controller/BlogController.java#L93-L107)

#### 16. 编辑博客表单

**接口地址**: `GET /blogs/{id}/edit`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 博客ID |

**响应**: HTML页面（Thymeleaf模板）

**章节来源**
- [BlogController.java](file://src/main/java/com/photo/controller/BlogController.java#L113-L128)

#### 17. 更新博客

**接口地址**: `POST /blogs/{id}`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 博客ID |
| title | String | 是 | 博客标题 |
| author | String | 是 | 博客作者 |
| content | String | 是 | 博客内容（Markdown格式） |
| category | String | 否 | 博客分类 |
| tags | String | 否 | 博客标签（逗号分隔） |

**响应**: 重定向到博客详情页面

**章节来源**
- [BlogController.java](file://src/main/java/com/photo/controller/BlogController.java#L134-L154)

#### 18. 删除博客

**接口地址**: `POST /blogs/{id}/delete`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 博客ID |

**响应**: 重定向到博客列表页面

**章节来源**
- [BlogController.java](file://src/main/java/com/photo/controller/BlogController.java#L160-L171)

### 照片管理API（保持不变）

#### 19. 上传单个照片

**接口地址**: `POST /photos/upload`

**请求类型**: `multipart/form-data`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| file | File | 是 | 图片文件 |
| userId | String | 否 | 用户ID，默认为"guest" |
| description | String | 否 | 照片描述 |

**文件限制**:
- 支持格式：JPG, JPEG, PNG, GIF, BMP, WEBP
- 最大大小：10MB

**成功响应示例**:
```json
{
  "code": 200,
  "message": "上传成功",
  "data": {
    "id": 1,
    "originalFilename": "photo.jpg",
    "storedFilename": "abc123def456.jpg",
    "fileSize": 1024000,
    "fileSizeReadable": "1.00 MB",
    "contentType": "image/jpeg",
    "url": "/api/photos/view/abc123def456.jpg",
    "thumbnailUrl": "/api/photos/thumbnail/abc123def456.jpg",
    "downloadUrl": "/api/photos/download/abc123def456.jpg",
    "width": 1920,
    "height": 1080,
    "uploadedAt": "2024-01-01T12:00:00",
    "md5": "abc123def456"
  },
  "timestamp": 1704110400000
}
```

**cURL示例**:
```bash
curl -X POST http://localhost:8080/api/photos/upload \
  -F "file=@/path/to/photo.jpg" \
  -F "userId=user123" \
  -F "description=我的照片"
```

**JavaScript示例**:
```javascript
async function uploadPhoto(file) {
  const formData = new FormData();
  formData.append('file', file);
  formData.append('userId', 'user123');
  
  const response = await fetch('http://localhost:8080/api/photos/upload', {
    method: 'POST',
    body: formData
  });
  
  const result = await response.json();
  console.log(result);
}
```

**Python示例**:
```python
import requests

def upload_photo(file_path):
    url = 'http://localhost:8080/api/photos/upload'
    files = {'file': open(file_path, 'rb')}
    data = {'userId': 'user123'}
    
    response = requests.post(url, files=files, data=data)
    return response.json()
```

**章节来源**
- [PhotoController.java](file://src/main/java/com/photo/controller/PhotoController.java#L48-L61)
- [PhotoService.java](file://src/main/java/com/photo/service/PhotoService.java#L50-L111)

#### 20. 批量上传照片

**接口地址**: `POST /photos/upload/batch`

**请求类型**: `multipart/form-data`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| files | File[] | 是 | 图片文件数组（最多10个） |
| userId | String | 否 | 用户ID，默认为"guest" |
| description | String | 否 | 照片描述 |

**成功响应示例**:
```json
{
  "code": 200,
  "message": "批量上传成功",
  "data": [
    {
      "id": 1,
      "originalFilename": "photo1.jpg",
      "storedFilename": "abc123def456.jpg",
      "fileSize": 1024000,
      "fileSizeReadable": "1.00 MB",
      "contentType": "image/jpeg",
      ...
    },
    {
      "id": 2,
      "originalFilename": "photo2.jpg",
      "storedFilename": "def456ghi789.jpg",
      "fileSize": 1536000,
      "fileSizeReadable": "1.47 MB",
      "contentType": "image/png",
      ...
    }
  ],
  "timestamp": 1704110400000
}
```

**cURL示例**:
```bash
curl -X POST http://localhost:8080/api/photos/upload/batch \
  -F "files=@/path/to/photo1.jpg" \
  -F "files=@/path/to/photo2.jpg" \
  -F "userId=user123"
```

**章节来源**
- [PhotoController.java](file://src/main/java/com/photo/controller/PhotoController.java#L66-L79)
- [PhotoService.java](file://src/main/java/com/photo/service/PhotoService.java#L116-L136)

#### 21. 在线预览照片

**接口地址**: `GET /photos/view/{filename}`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| filename | String | 是 | 文件名（路径参数） |

**响应**: 直接返回图片二进制流

**响应头**:
- `Content-Type`: image/jpeg (或其他图片类型)
- `Cache-Control`: max-age=3600

**示例**:
```
http://localhost:8080/api/photos/view/abc123def456.jpg
```

**章节来源**
- [PhotoController.java](file://src/main/java/com/photo/controller/PhotoController.java#L84-L117)
- [SecurityUtils.java](file://src/main/java/com/photo/util/SecurityUtils.java#L60-L80)

#### 22. 查看缩略图

**接口地址**: `GET /photos/thumbnail/{filename}`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| filename | String | 是 | 文件名（路径参数） |

**响应**: 返回缩略图二进制流（200x200像素）

**示例**:
```
http://localhost:8080/api/photos/thumbnail/abc123def456.jpg
```

**章节来源**
- [PhotoController.java](file://src/main/java/com/photo/controller/PhotoController.java#L122-L144)
- [FileStorageService.java](file://src/main/java/com/photo/service/FileStorageService.java#L146-L165)

#### 23. 下载照片

**接口地址**: `GET /photos/download/{filename}`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| filename | String | 是 | 文件名（路径参数） |

**响应**: 返回文件下载流

**响应头**:
- `Content-Type`: application/octet-stream
- `Content-Disposition`: attachment; filename="original_filename.jpg"

**示例**:
```
http://localhost:8080/api/photos/download/abc123def456.jpg
```

**章节来源**
- [PhotoController.java](file://src/main/java/com/photo/controller/PhotoController.java#L149-L179)
- [PhotoService.java](file://src/main/java/com/photo/service/PhotoService.java#L238-L249)

#### 24. 断点续传下载

**接口地址**: `GET /photos/download/range/{filename}`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| filename | String | 是 | 文件名（路径参数） |

**请求头**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| Range | String | 否 | 字节范围，如 "bytes=0-1023" |

**响应**: 返回指定范围的文件内容

**响应头**:
- `Content-Range`: bytes 0-1023/10240
- `Accept-Ranges`: bytes

**HTTP状态码**: 206 (Partial Content)

**cURL示例**:
```bash
curl -H "Range: bytes=0-1023" \
  http://localhost:8080/api/photos/download/range/abc123def456.jpg
```

**章节来源**
- [PhotoController.java](file://src/main/java/com/photo/controller/PhotoController.java#L184-L225)
- [FileStorageService.java](file://src/main/java/com/photo/service/FileStorageService.java#L229-L257)

#### 25. 获取照片信息

**接口地址**: `GET /photos/{id}`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 照片ID（路径参数） |

**成功响应示例**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 1,
    "originalFilename": "photo.jpg",
    "fileSize": 1024000,
    "fileSizeReadable": "1.00 MB",
    "contentType": "image/jpeg",
    "url": "/api/photos/view/abc123def456.jpg",
    "thumbnailUrl": "/api/photos/thumbnail/abc123def456.jpg",
    "downloadUrl": "/api/photos/download/abc123def456.jpg",
    "width": 1920,
    "height": 1080,
    "accessCount": 10,
    "downloadCount": 5,
    "isPublic": true,
    "description": "我的照片",
    "createdAt": "2024-01-01T12:00:00",
    "updatedAt": "2024-01-01T12:00:00",
    "lastAccessedAt": "2024-01-01T13:00:00"
  },
  "timestamp": 1704110400000
}
```

**章节来源**
- [PhotoController.java](file://src/main/java/com/photo/controller/PhotoController.java#L230-L237)
- [PhotoService.java](file://src/main/java/com/photo/service/PhotoService.java#L141-L151)

#### 26. 获取用户照片列表

**接口地址**: `GET /photos/user/{userId}`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| userId | String | 是 | 用户ID（路径参数） |
| page | Integer | 否 | 页码，从0开始，默认0 |
| size | Integer | 否 | 每页数量，默认20 |

**成功响应示例**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "content": [...],
    "pageable": {...},
    "totalPages": 5,
    "totalElements": 100,
    "size": 20,
    "number": 0
  },
  "timestamp": 1704110400000
}
```

**章节来源**
- [PhotoController.java](file://src/main/java/com/photo/controller/PhotoController.java#L242-L251)
- [PhotoService.java](file://src/main/java/com/photo/service/PhotoService.java#L165-L169)

#### 27. 获取公开照片列表

**接口地址**: `GET /photos/public`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | Integer | 否 | 页码，从0开始，默认0 |
| size | Integer | 否 | 每页数量，默认20 |

**成功响应示例**: 同上

**章节来源**
- [PhotoController.java](file://src/main/java/com/photo/controller/PhotoController.java#L256-L264)
- [PhotoService.java](file://src/main/java/com/photo/service/PhotoService.java#L174-L178)

#### 28. 搜索照片

**接口地址**: `GET /photos/search`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| keyword | String | 是 | 搜索关键词 |
| page | Integer | 否 | 页码，从0开始，默认0 |
| size | Integer | 否 | 每页数量，默认20 |

**成功响应示例**: 同上

**示例**:
```
http://localhost:8080/api/photos/search?keyword=vacation&page=0&size=10
```

**章节来源**
- [PhotoController.java](file://src/main/java/com/photo/controller/PhotoController.java#L269-L278)
- [PhotoService.java](file://src/main/java/com/photo/service/PhotoService.java#L183-L187)

#### 29. 删除照片（软删除）

**接口地址**: `DELETE /photos/{id}`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 照片ID（路径参数） |
| userId | String | 是 | 用户ID（查询参数） |

**成功响应示例**:
```json
{
  "code": 200,
  "message": "删除成功",
  "data": null,
  "timestamp": 1704110400000
}
```

**cURL示例**:
```bash
curl -X DELETE "http://localhost:8080/api/photos/1?userId=user123"
```

**章节来源**
- [PhotoController.java](file://src/main/java/com/photo/controller/PhotoController.java#L283-L291)
- [PhotoService.java](file://src/main/java/com/photo/service/PhotoService.java#L192-L205)

#### 30. 永久删除照片

**接口地址**: `DELETE /photos/{id}/permanent`

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 照片ID（路径参数） |
| userId | String | 是 | 用户ID（查询参数） |

**成功响应示例**:
```json
{
  "code": 200,
  "message": "永久删除成功",
  "data": null,
  "timestamp": 1704110400000
}
```

**章节来源**
- [PhotoController.java](file://src/main/java/com/photo/controller/PhotoController.java#L296-L304)
- [PhotoService.java](file://src/main/java/com/photo/service/PhotoService.java#L208-L234)

#### 31. 获取存储空间信息

**接口地址**: `GET /photos/storage/info`

**成功响应示例**:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "usedSpace": 1024000000,
    "usedSpaceReadable": "976.56 MB",
    "totalSpace": 10737418240,
    "totalSpaceReadable": "10.00 GB",
    "freeSpace": 9713418240,
    "freeSpaceReadable": "9.05 GB",
    "usagePercentage": 9.54,
    "totalFiles": 150
  },
  "timestamp": 1704110400000
}
```

**章节来源**
- [PhotoController.java](file://src/main/java/com/photo/controller/PhotoController.java#L309-L314)
- [PhotoService.java](file://src/main/java/com/photo/service/PhotoService.java#L254-L271)

## 安全说明

1. **表单认证**: 使用Spring Security实现用户名密码认证
2. **密码加密**: 使用BCrypt对用户密码进行哈希加密
3. **会话管理**: 基于HttpSession的用户会话管理
4. **CORS配置**: 支持跨域资源共享配置
5. **文件类型验证**: 使用Apache Tika进行MIME类型检测
6. **文件大小限制**: 单文件最大10MB，可在配置文件中调整
7. **防盗链**: 可配置允许的Referer域名列表
8. **XSS防护**: 对所有输入进行HTML转义
9. **路径遍历防护**: 严格验证文件名，防止目录遍历攻击
10. **访问权限控制**: 私有照片仅所有者可访问

**章节来源**
- [SecurityConfig.java](file://src/main/java/com/photo/config/SecurityConfig.java#L36-L58)
- [AuthController.java](file://src/main/java/com/photo/controller/AuthController.java#L47-L90)
- [SecurityUtils.java](file://src/main/java/com/photo/util/SecurityUtils.java#L14-L167)
- [application.yml](file://src/main/resources/application.yml#L120-L145)

## 性能优化

1. **缓存机制**: 使用Caffeine缓存照片元数据
2. **图片压缩**: 自动压缩大尺寸图片
3. **缩略图**: 自动生成200x200缩略图
4. **断点续传**: 支持Range请求，适合大文件下载
5. **数据库索引**: 对常用查询字段建立索引
6. **模板缓存**: Thymeleaf模板缓存配置
7. **连接池**: Tomcat连接池和线程池配置

**章节来源**
- [application.yml](file://src/main/resources/application.yml#L50-L63)
- [application.yml](file://src/main/resources/application.yml#L161-L172)
- [FileStorageProperties.java](file://src/main/java/com/photo/config/FileStorageProperties.java#L78-L85)

## 数据库设计

### 用户表（users）

| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 用户ID |
| username | VARCHAR(50) | NOT NULL, UNIQUE | 用户名 |
| password | VARCHAR(255) | NOT NULL | 加密密码 |
| email | VARCHAR(100) | NULL | 邮箱地址 |
| enabled | BOOLEAN | DEFAULT TRUE | 是否启用 |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| last_login | TIMESTAMP | NULL | 最后登录时间 |

### 便签表（notes）

| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 便签ID |
| title | VARCHAR(200) | NOT NULL | 便签标题 |
| content | TEXT | NULL | 便签内容（Markdown） |
| created_time | TIMESTAMP | NOT NULL | 创建时间 |
| updated_time | TIMESTAMP | NOT NULL | 更新时间 |

**索引**:
- idx_created_time: created_time

### 博客表（blogs）

| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 博客ID |
| title | VARCHAR(300) | NOT NULL | 博客标题 |
| author | VARCHAR(100) | NOT NULL | 博客作者 |
| content | TEXT | NULL | 博客内容（Markdown） |
| category | VARCHAR(50) | NULL | 博客分类 |
| tags | VARCHAR(200) | NULL | 博客标签 |
| created_time | TIMESTAMP | NOT NULL | 创建时间 |
| updated_time | TIMESTAMP | NOT NULL | 更新时间 |

**索引**:
- idx_blog_created_time: created_time
- idx_blog_author: author

**章节来源**
- [schema.sql](file://src/main/resources/schema.sql#L4-L144)
- [Note.java](file://src/main/java/com/photo/entity/Note.java#L16-L58)
- [Blog.java](file://src/main/java/com/photo/entity/Blog.java#L16-L77)

## Swagger文档

访问以下地址查看完整的交互式API文档：

```
http://localhost:8080/api/swagger-ui.html
```

或OpenAPI规范JSON：

```
http://localhost:8080/api/api-docs
```

**章节来源**
- [OpenApiConfig.java](file://src/main/java/com/photo/config/OpenApiConfig.java#L16-L30)
- [application.yml](file://src/main/resources/application.yml#L183-L190)