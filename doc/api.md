# API
## Получение данных при входе в систему
```json
{
	"jsonrpc":"2.0",
	"id":1234,
	"method":"get_on_start",
	"params": 
	{
    	"initiator":"my_id",
    	"friends":[
    	{
    		"nickname":"nickname1",
    		"name": "frien_name",
    		"status": "on/off"
    	}],
    	"files":[{
    	"name": "file1"
    	},
    	{
    	"name": "file2"
    	}
    	]
    }
}
```
## Ответ на создание чата от initiator пользователям nickname1, nickname2
```json
{
	"jsonrpc":"2.0",
	"id":1234,
	"method":"chat_create",
	"params": 
	{
    	"initiator":"my_id",
    	"name": "new_chat", 
    	"user_nicknames":["nickname1","nickname2"]
    }
}
```
## Ответ на создание чата от одного из пользователей
```json
{
	"jsonrpc":"2.0",
	"id":1234,
	"method":"chat_create",
	"params": 
	{
    	"user_id":"id",
    	"char_id": "new_chat",
    	"name":"chat_name"
    }
}
```
## Отправка сообщения
```json
{
	"jsonrpc":"2.0",
	"id":1234,
	"method":"send_msg",
	"params": 
	{
    	"nickname":"my_nickname",
    	"name": "chat_id",	
    	"msg": "msg1",
    	"attached_files":[
    		{
    			"name":"filename1",
    			"ID": "id1"
    		}
    	]
    }
}
```
## Получение сообщения
```json
{
	jsonrpc":"2.0",
	"id":1234,
	"method":"send_msg",
	"params": 
	{
    	"name":"sender_id",
    	"name": "chat_id",	
    	"msg": "msg1",
    	"attached_files":[
    		{
    			"name":"filename1",
    			"ID":"id1"
    		}
    	]
    }
}
```
## Отправить заявку в друзья
```json
{
	jsonrpc":"2.0",
	"id":1234,
	"method":"add_friend",
	"params": 
	{
    	"my_nickname":"nickname",
    	"nickname": "nickname1"
    }
}
```
## Ответ на заявку в друзья
```json
{
	jsonrpc":"2.0",
	"id":1234,
	"method":"add_friend",
	"params": 
	{
    	"result":"ok",
    	"nickname": "friend_nickname",
    	"name":"friend_name",
    }
}
```
## Запрос на изменение имени
```json
{
	jsonrpc":"2.0",
	"id":1234,
	"method":"change_name",
	"params": 
	{
		"nickname": "my_nickname",
    	"name":"my_new_name"
    }
}
```
## Ответ пользователю на запрос об изменении имени
```json
{
	jsonrpc":"2.0",
	"id":1234,
	"method":"change_name",
	"params": 
	{
    	"name":"my_new_name"
    }
}
```
## Оповещение об изменении имени друга
```json
{
	jsonrpc":"2.0",
	"id":1234,
	"method":"change_name",
	"params": 
	{
		"friend_nickname": "nickname1",
    	"name":"new_name"
    }
}
```
## Запрос на изменение пароля (email аналогично)
```json
{
	jsonrpc":"2.0",
	"id":1234,
	"method":"change_password",
	"params": 
	{
		"nickname": "my_nickname",
    	"old":"my_old_password",
    	"new": "my_new_password"	
    }
}
```
## Подтверждение изменения пароля
```json
{
	jsonrpc":"2.0",
	"id":1234,
	"method":"change_password",
	"params": 
	{
		"nickname": "my_nickname"	
    }
}
```
## Уведомление о входе/выходе друга в сеть
```json
{
	jsonrpc":"2.0",
	"id":1234,
	"method":"friend_status",
	"params": 
	{
		"nickname": "nickname1",
		"status":"online"
    }
}
```