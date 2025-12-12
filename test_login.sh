#!/bin/bash

# 登录功能测试脚本
# 该脚本用于测试登录API的基本功能

echo "=================================="
echo "登录功能测试脚本"
echo "=================================="
echo ""

BASE_URL="http://localhost:8080"
COOKIE_FILE="/tmp/login_test_cookies.txt"

# 清理旧的cookie文件
rm -f $COOKIE_FILE

echo "1. 测试登录接口 - 正确的用户名和密码"
echo "----------------------------------------"
RESPONSE=$(curl -s -w "\n%{http_code}" -X POST \
  -H "Content-Type: application/json" \
  -c $COOKIE_FILE \
  -d '{"username":"admin","password":"admin123","rememberMe":false}' \
  ${BASE_URL}/api/auth/login)

HTTP_CODE=$(echo "$RESPONSE" | tail -n 1)
BODY=$(echo "$RESPONSE" | sed '$d')

echo "HTTP状态码: $HTTP_CODE"
echo "响应内容: $BODY"

if [ "$HTTP_CODE" = "200" ]; then
    echo "✅ 测试通过: 登录成功"
else
    echo "❌ 测试失败: 登录失败"
fi
echo ""

echo "2. 测试检查登录状态接口"
echo "----------------------------------------"
RESPONSE=$(curl -s -w "\n%{http_code}" -X GET \
  -b $COOKIE_FILE \
  ${BASE_URL}/api/auth/status)

HTTP_CODE=$(echo "$RESPONSE" | tail -n 1)
BODY=$(echo "$RESPONSE" | sed '$d')

echo "HTTP状态码: $HTTP_CODE"
echo "响应内容: $BODY"

if [ "$HTTP_CODE" = "200" ]; then
    echo "✅ 测试通过: 已登录状态"
else
    echo "❌ 测试失败: 未检测到登录状态"
fi
echo ""

echo "3. 测试访问受保护的资源"
echo "----------------------------------------"
RESPONSE=$(curl -s -w "\n%{http_code}" -X GET \
  -b $COOKIE_FILE \
  ${BASE_URL}/api/photos/public?page=0&size=10)

HTTP_CODE=$(echo "$RESPONSE" | tail -n 1)
echo "HTTP状态码: $HTTP_CODE"

if [ "$HTTP_CODE" = "200" ]; then
    echo "✅ 测试通过: 可以访问受保护的资源"
else
    echo "⚠️  需要检查: HTTP状态码 $HTTP_CODE"
fi
echo ""

echo "4. 测试登出接口"
echo "----------------------------------------"
RESPONSE=$(curl -s -w "\n%{http_code}" -X POST \
  -b $COOKIE_FILE \
  ${BASE_URL}/api/auth/logout)

HTTP_CODE=$(echo "$RESPONSE" | tail -n 1)
BODY=$(echo "$RESPONSE" | sed '$d')

echo "HTTP状态码: $HTTP_CODE"
echo "响应内容: $BODY"

if [ "$HTTP_CODE" = "200" ]; then
    echo "✅ 测试通过: 登出成功"
else
    echo "❌ 测试失败: 登出失败"
fi
echo ""

echo "5. 测试登出后的状态检查"
echo "----------------------------------------"
RESPONSE=$(curl -s -w "\n%{http_code}" -X GET \
  -b $COOKIE_FILE \
  ${BASE_URL}/api/auth/status)

HTTP_CODE=$(echo "$RESPONSE" | tail -n 1)
BODY=$(echo "$RESPONSE" | sed '$d')

echo "HTTP状态码: $HTTP_CODE"
echo "响应内容: $BODY"

if [ "$HTTP_CODE" = "401" ]; then
    echo "✅ 测试通过: 已成功登出"
else
    echo "❌ 测试失败: 登出状态异常"
fi
echo ""

echo "6. 测试错误的用户名密码"
echo "----------------------------------------"
RESPONSE=$(curl -s -w "\n%{http_code}" -X POST \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"wrongpassword","rememberMe":false}' \
  ${BASE_URL}/api/auth/login)

HTTP_CODE=$(echo "$RESPONSE" | tail -n 1)
BODY=$(echo "$RESPONSE" | sed '$d')

echo "HTTP状态码: $HTTP_CODE"
echo "响应内容: $BODY"

if [ "$HTTP_CODE" = "200" ] && echo "$BODY" | grep -q "401"; then
    echo "✅ 测试通过: 正确拒绝错误的密码"
else
    echo "⚠️  需要检查: 响应码或内容异常"
fi
echo ""

echo "7. 测试不存在的用户"
echo "----------------------------------------"
RESPONSE=$(curl -s -w "\n%{http_code}" -X POST \
  -H "Content-Type: application/json" \
  -d '{"username":"nonexistent","password":"password123","rememberMe":false}' \
  ${BASE_URL}/api/auth/login)

HTTP_CODE=$(echo "$RESPONSE" | tail -n 1)
BODY=$(echo "$RESPONSE" | sed '$d')

echo "HTTP状态码: $HTTP_CODE"
echo "响应内容: $BODY"

if [ "$HTTP_CODE" = "200" ] && echo "$BODY" | grep -q "401"; then
    echo "✅ 测试通过: 正确拒绝不存在的用户"
else
    echo "⚠️  需要检查: 响应码或内容异常"
fi
echo ""

# 清理cookie文件
rm -f $COOKIE_FILE

echo "=================================="
echo "测试完成"
echo "=================================="
echo ""
echo "说明:"
echo "- 确保应用已启动在 http://localhost:8080"
echo "- 确保数据库已初始化默认用户(admin/admin123)"
echo "- 如果测试失败,请检查应用日志"
