#sneaker
***技术栈***  
*springboot+mybatis+mysql*  
*rabbitmq进行异步下单*  
*redis做登陆鉴权和数据缓存*

## 接口说明（所有请求皆为json格式）
>登陆接口    POST   
 /user/wx/login
```json
{
	"wx_code":"wxcode"
}
```
>返回示例
```json
{
    "data": {
        "userId": "123456",
        "token": "1234564054bb9a4fe8a47d925f"
    },
    "errCode": 0,
    "errMsg": "成功"
}
```
>用户信息接口 （需要登录）   GET   
 /user/info 请求头为登陆接口返回的userId_token，如下
```
{
	"token":"userId_token"
}
```
>返回示例
```json
{
    "data": {
        "userName": "zzl",
        "passWord": "77efb622dd4b2893e9979da5b629d529",
        "role": 1,
        "wxOpenId": null,
        "headImage": "/image",
        "loginCount": 0,
        "createTime": "2018-08-15T09:37:02.000+0000",
        "updateTime": "2018-08-15T09:37:01.000+0000",
        "id": "1534325821745303440"
    },
    "errCode": 0,
    "errMsg": "成功"
}
```
>查询所有商品类型 （无需登录）   GET   
/product/category/list_all

>返回示例
```json
{
    "data": {
        "productcategory_list": [
            {
                "categoryId": 1,
                "categoryName": "休闲鞋",
                "categoryType": 1,
                "createTime": "2018-07-26T09:17:53.000+0000",
                "updateTime": "2018-07-26T09:17:56.000+0000"
            },
            {
                "categoryId": 2,
                "categoryName": "运动鞋",
                "categoryType": 2,
                "createTime": "2018-07-26T09:21:08.000+0000",
                "updateTime": "2018-07-26T09:21:11.000+0000"
            }
        ]
    },
    "errCode": 0,
    "errMsg": "成功"
}
```
>通过id获取商品详情 （无需登录）   GET   
 /product/product_info/detail?product_id={product_id}  
>返回示例
```json
{
    "data": {
        "productId": "223",
        "productName": "air Jordan11",
        "productPrice": 1499,
        "productStock": 92,
        "productDescription": "air Jordan",
        "productIcon": "/image/nike/aj",
        "productStatus": 0,
        "categoryType": 11,
        "createTime": "2018-08-02T04:20:07.000+0000",
        "updateTime": "2018-08-26T02:46:10.000+0000"
    },
    "errCode": 0,
    "errMsg": "成功"
}
```
>通过类型查询商品详情（无需登录）    POST   
/product/product_info/list
```json
{
	"type_list":[1],
	"page":0,
	"size":10
}
```
>返回示例
```json
{
    "data": [
        {
            "productId": "1",
            "productName": "zx750",
            "productPrice": 699,
            "productStock": 8,
            "productDescription": "zx系列",
            "productIcon": "/image",
            "productStatus": 0,
            "categoryType": 1,
            "createTime": "2018-07-26T10:53:10.000+0000",
            "updateTime": "2018-08-23T09:54:05.000+0000"
        },
        {
            "productId": "111",
            "productName": "zx630",
            "productPrice": 899,
            "productStock": 110,
            "productDescription": "zx系列",
            "productIcon": "/image",
            "productStatus": 0,
            "categoryType": 1,
            "createTime": "2018-08-10T07:04:55.000+0000",
            "updateTime": "2018-08-13T10:47:25.000+0000"
        }
    ],
    "errCode": 0,
    "errMsg": "成功"
}
```