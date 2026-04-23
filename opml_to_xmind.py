#!/usr/bin/env python3
"""OPML to XMind Cloud converter"""

import zipfile
import json
import xml.etree.ElementTree as ET
import uuid
import re
import sys
from pathlib import Path

def sanitize_text(text):
    if not text:
        return ""
    text = re.sub(r'[\x00-\x08\x0b\x0c\x0e-\x1f\x7f]', '', text)
    return text.strip()

def generate_id():
    return str(uuid.uuid4())[:8]

def convert_outline(outline, ns):
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
    tree = ET.parse(opml_path)
    root = tree.getroot()

    ns = {}
    match = re.match(r'\{(.+)\}', root.tag)
    if match:
        ns = {'opml': match.group(1)}

    # Use correct xpath based on namespace presence
    body = None
    if ns:
        body = root.find('.//opml:body', ns)
    else:
        body = root.find('.//body')
    if body is None:
        body = root

    # Get only DIRECT children of body, not nested outlines
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

if __name__ == '__main__':
    if len(sys.argv) < 3:
        print("Usage: python opml_to_xmind.py <input.opml> <output.xmind>")
        sys.exit(1)

    result = opml_to_xmind(sys.argv[1], sys.argv[2])
    print(f"转换完成: {result}")
