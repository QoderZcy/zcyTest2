---
name: opml-to-xmind
description: "将OPML大纲文件转换为XMind Cloud格式思维导图。用于：(1) OPML文件转换为.xmind文件，(2) 创建思维导图，(3) 大纲转思维导图"
---

# OPML to XMind Converter

## Overview

此skill将OPML格式的大纲文件转换为XMind Cloud格式（.xmind）的思维导图文件。XMind Cloud是XMind 2024+使用的新格式，是一个ZIP压缩包，包含JSON格式的内容文件。

## OPML Format

OPML (Outline Processor Markup Language) 是XML格式的大纲文件：
- `<opml>`: 根元素
- `<body>`: 内容主体
- `<outline>`: 大纲节点，可递归嵌套
  - `text` 属性: 节点文本（必需）
  - `_note` 属性: 节点备注
  - 其他自定义属性

示例OPML结构：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<opml version="2.0">
  <body>
    <outline text="根节点">
      <outline text="子节点1">
        <outline text="孙节点1.1"/>
        <outline text="孙节点1.2"/>
      </outline>
      <outline text="子节点2"/>
    </outline>
  </body>
</opml>
```

## XMind Cloud Format

XMind Cloud格式是ZIP压缩包，包含：
- `content.json`: 思维导图内容
- `metadata.json`: 元数据文件

### content.json 结构
```json
{
  "id": "map_uuid",
  "title": "思维导图标题",
  "rootTopic": {
    "id": "topic_uuid",
    "text": "根节点文本",
    "children": {
      "attached": [
        {
          "id": "child_uuid",
          "text": "子节点文本",
          "children": { "attached": [...] }
        }
      ]
    }
  }
}
```

## Implementation

### 使用Python脚本转换

```python
import zipfile
import json
import xml.etree.ElementTree as ET
import uuid
import re
from pathlib import Path

def sanitize_text(text):
    """清理文本，移除控制字符"""
    if not text:
        return ""
    text = re.sub(r'[\x00-\x08\x0b\x0c\x0e-\x1f\x7f]', '', text)
    return text.strip()

def generate_id():
    """生成简短的UUID"""
    return str(uuid.uuid4())[:8]

def convert_outline(outline, ns):
    """将OPML outline节点转换为XMind topic结构"""
    topic = {
        "id": generate_id(),
        "text": sanitize_text(outline.get('text', ''))
    }

    note = outline.get('_note') or outline.get('note')
    if note:
        topic["notes"] = {"plain": {"text": sanitize_text(note)}}

    # Find direct children only
    if ns:
        children = outline.findall('opml:outline', ns)
    else:
        children = [c for c in outline if c.tag == 'outline']

    if children:
        topic["children"] = {
            "attached": [convert_outline(child, ns) for child in children]
        }

    return topic

def opml_to_xmind(opml_path, xmind_path):
    """将OPML文件转换为XMind Cloud格式"""
    tree = ET.parse(opml_path)
    root = tree.getroot()

    ns = {}
    match = re.match(r'\{(.+)\}', root.tag)
    if match:
        ns = {'opml': match.group(1)}

    # Find body
    body = None
    if ns:
        body = root.find('.//opml:body', ns)
    else:
        body = root.find('.//body')
    if body is None:
        body = root

    # Get only DIRECT children of body
    if ns:
        outlines = body.findall('opml:outline', ns)
    else:
        outlines = [c for c in body if c.tag == 'outline']

    root_topic = {"id": generate_id(), "text": "Central Topic"}

    if len(outlines) == 1:
        root_topic = convert_outline(outlines[0], ns)
    elif len(outlines) > 1:
        root_topic["children"] = {
            "attached": [convert_outline(o, ns) for o in outlines]
        }

    title = outlines[0].get('text', Path(opml_path).stem) if outlines else Path(opml_path).stem

    xmind_content = {
        "id": generate_id(),
        "title": sanitize_text(title),
        "rootTopic": root_topic,
        "dataLayers": []
    }

    metadata = {
        "creator": {"name": "Qoder CLI", "version": "1.0"},
        "createTime": None,
        "modifyTime": None
    }

    with zipfile.ZipFile(xmind_path, 'w', zipfile.ZIP_DEFLATED) as zf:
        zf.writestr('content.json', json.dumps(xmind_content, indent=2, ensure_ascii=False))
        zf.writestr('metadata.json', json.dumps(metadata, indent=2, ensure_ascii=False))

    return xmind_path
```

## Usage Instructions

### 方式1: 直接运行Python脚本
```bash
python opml_to_xmind.py input.opml output.xmind
```

### 方式2: 在Qoder CLI中调用
用户只需提供OPML文件路径，Qoder CLI会自动使用此skill进行转换。

### 方式3: 在Python代码中使用
```python
from opml_to_xmind import opml_to_xmind
opml_to_xmind('outline.opml', 'mindmap.xmind')
```

## Features

- 支持嵌套任意深度的OPML结构
- 自动处理命名空间
- 支持节点备注（_note属性）
- 保留文本格式
- 生成符合XMind Cloud规范的文件
- 输出文件可直接在XMind 2024+中打开

## Limitations

- 样式信息（如颜色、字体）会被忽略
- 图片等媒体内容不会被转换
- 仅支持基本的大纲结构转换

## Requirements

- Python 3.7+
- 标准库（无需额外依赖）
