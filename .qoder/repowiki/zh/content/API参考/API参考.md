# API参考

<cite>
**本文档中引用的文件**
- [API_DOCUMENTATION.md](file://API_DOCUMENTATION.md)
- [PhotoController.java](file://src/main/java/com/photo/controller/PhotoController.java)
- [ApiResponse.java](file://src/main/java/com/photo/dto/ApiResponse.java)
- [PhotoDTO.java](file://src/main/java/com/photo/dto/PhotoDTO.java)
- [PhotoUploadResponse.java](file://src/main/java/com/photo/dto/PhotoUploadResponse.java)
- [StorageInfo.java](file://src/main/java/com/photo/dto/StorageInfo.java)
- [PhotoService.java](file://src/main/java/com/photo/service/PhotoService.java)
- [FileStorageService.java](file://src/main/java/com/photo/service/FileStorageService.java)
- [SecurityUtils.java](file://src/main/java/com/photo/util/SecurityUtils.java)
- [application.yml](file://src/main/resources/application.yml)
- [FileStorageProperties.java](file://src/main/java/com/photo/config/FileStorageProperties.java)
- [GlobalExceptionHandler.java](file://src/main/java/com/photo/exception/GlobalExceptionHandler.java)
- [OpenApiConfig.java](file://src/main/java/com/photo/config/OpenApiConfig.java)
</cite>

## 目录
1. [简介](#简介)
2. [基础信息](#基础信息)
3. [统一响应格式](#统一响应格式)
4. [错误码说明](#错误码说明)
5. [API接口详情](#api接口详情)
6. [安全说明](#安全说明)
7. [性能优化](#性能优化)
8. [Swagger文档](#swagger文档)

## 简介

本文档详细介绍了基于Spring Boot构建的照片上传下载系统的13个RESTful API接口。该系统提供了完整的照片管理功能，包括上传、下载、预览、搜索、删除等操作，并具备完善的错误处理和安全机制。

## 基础信息

- **Base URL**: `http://localhost:8080/api`
- **API版本**: v1.0.0
- **认证方式**: 暂无（可扩展JWT认证）
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
| 400 | 请求参数错误 | "不支持的文件类型: text/plain" |
| 400 | 文件大小超限 | "文件大小不能超过 10.00 MB" |
| 403 | 访问被拒绝 | "无权删除该照片" |
| 403 | 非法访问来源 | "非法访问来源" |
| 404 | 资源不存在 | "照片不存在: 123" |
| 500 | 服务器内部错误 | "文件存储失败" |
| 507 | 存储空间不足 | "存储空间不足" |

**章节来源**
- [GlobalExceptionHandler.java](file://src/main/java/com/photo/exception/GlobalExceptionHandler.java#L24-L138)

## API接口详情

### 1. 上传单个照片

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

### 2. 批量上传照片

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

### 3. 在线预览照片

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

### 4. 查看缩略图

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

### 5. 下载照片

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

### 6. 断点续传下载

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

### 7. 获取照片信息

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

### 8. 获取用户照片列表

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

### 9. 获取公开照片列表

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

### 10. 搜索照片

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

### 11. 删除照片（软删除）

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

### 12. 永久删除照片

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

### 13. 获取存储空间信息

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

1. **文件类型验证**: 使用Apache Tika进行MIME类型检测，不仅依赖文件扩展名
2. **文件大小限制**: 单文件最大10MB，可在配置文件中调整
3. **防盗链**: 可配置允许的Referer域名列表
4. **XSS防护**: 对所有输入进行HTML转义
5. **路径遍历防护**: 严格验证文件名，防止目录遍历攻击
6. **访问权限控制**: 私有照片仅所有者可访问

**章节来源**
- [SecurityUtils.java](file://src/main/java/com/photo/util/SecurityUtils.java#L14-L167)
- [application.yml](file://src/main/resources/application.yml#L100-L118)

## 性能优化

1. **缓存机制**: 使用Caffeine缓存照片元数据
2. **图片压缩**: 自动压缩大尺寸图片
3. **缩略图**: 自动生成200x200缩略图
4. **断点续传**: 支持Range请求，适合大文件下载
5. **数据库索引**: 对常用查询字段建立索引

**章节来源**
- [application.yml](file://src/main/resources/application.yml#L45-L49)
- [FileStorageProperties.java](file://src/main/java/com/photo/config/FileStorageProperties.java#L78-L85)

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
- [application.yml](file://src/main/resources/application.yml#L167-L173)