#sneaker
***技术栈***  
*springboot+mybatis+mysql*  
*rabbitmq进行异步下单*  
*redis做登陆鉴权和数据缓存*

#### 接口说明（所有请求皆为json格式，需要登录的接口填入请求头token:userId_token）
##  用户相关
>登陆接口    POST   
/user/login
```json
{
	"user_name":"zl",
	"pass_word":"123123"
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
##  商品相关
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
>通过类型id筛选商品类型（无需登录）    POST   
/product/category/list
```json
{
	"type_list":[1,2,11]
}
```
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
            },
            {
                "categoryId": 3,
                "categoryName": "air Jordan",
                "categoryType": 11,
                "createTime": "2018-08-02T03:07:22.000+0000",
                "updateTime": "2018-08-02T03:07:26.000+0000"
            }
        ]
    },
    "errCode": 0,
    "errMsg": "成功"
}
```
##  订单相关
>创建订单（需要登录）    POST   
/order/create  
```json
{
	"buyer_name":"周先生",
	"buyer_phone":"135757557",
	"buyer_address":"这是收货地址",
	"open_id":"wx1827218312378",
	"item":[
		{
			"product_id":"1",
			"product_quantity":1
		},
				{
			"product_id":"122",
			"product_quantity":1
		}
		]
}
```
>返回示例
```json
{
    "data": {
        "order_id": "1535363331937662483"
    },
    "errCode": 0,
    "errMsg": "成功"
}
```
>取消订单（需要登录）    POST   
/order/cancel 
```json
{
	"order_id":"1535018045764550149"
}
```
>返回示例
```json
{
    "data": {
        "state": 2,
        "state_info": "取消",
        "order_id": "1535018045764550149",
        "buyer_name": "周先生",
        "buyer_phone": "135757557",
        "buyer_address": "这是收货地址",
        "buyer_openid": "1534325821745303440",
        "order_amount": 2698,
        "order_status": 2,
        "pay_status": 0,
        "create_time": "2018-08-23T09:54:05.000+0000",
        "update_time": "2018-08-27T08:36:56.000+0000",
        "order_detailList": null,
        "order_master": null,
        "order_master_list": null
    },
    "errCode": 0,
    "errMsg": "成功"
}
```
>根据userid查询订单列表（需要登录）    GET   
/order/list?page=1&size=10  
>返回示例
```json
{
    "data": [
        {
            "orderId": "1535018045764550149",
            "buyerName": "周先生",
            "buyerPhone": "135757557",
            "buyerAddress": "这是收货地址",
            "buyerOpenid": "1534325821745303440",
            "orderAmount": 2698,
            "orderStatus": 0,
            "payStatus": 0,
            "createTime": "2018-08-23T09:54:05.000+0000",
            "updateTime": "2018-08-23T09:54:05.000+0000"
        },
        {
            "orderId": "1535011592301988512",
            "buyerName": "周先生",
            "buyerPhone": "135757557",
            "buyerAddress": "这是收货地址",
            "buyerOpenid": "1534325821745303440",
            "orderAmount": 2698,
            "orderStatus": 0,
            "payStatus": 0,
            "createTime": "2018-08-23T08:06:32.000+0000",
            "updateTime": "2018-08-23T08:06:32.000+0000"
        }
    ],
    "errCode": 0,
    "errMsg": "成功"
}
```
>根据orderid查询订单详情（需要登录）    POST   
/order/detail
```json
{
	"order_id":"1535363876210728125"
}
```
>返回示例
```json
{
    "data": {
        "orderId": "1535363876210728125",
        "buyerName": "周先生",
        "buyerPhone": "135757557",
        "buyerAddress": "这是收货地址",
        "buyerOpenid": "1534325856245904736",
        "orderAmount": 2698,
        "orderStatus": 0,
        "payStatus": 0,
        "createTime": "2018-08-27T09:57:56.000+0000",
        "updateTime": "2018-08-27T09:57:56.000+0000"
    },
    "errCode": 0,
    "errMsg": "成功"
}
```