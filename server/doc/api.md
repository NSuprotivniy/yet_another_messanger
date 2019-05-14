## Создание чата
#### request
``` json
{
        "version": "2.0",
        "method": "chat_create",
        "params": {
                "text": "new chatUUID"
        },
        "id": 1234
}
```

#### response
``` json
{
	"params": {
		"uuid":"900a0d80-5b50-11e9-8be6-0a0027000001"
	},
	"id":1234,
	"jsonrpc":"2.0",
	"method":"chat_create"
}
```

## Создание пользователя
#### request
``` json
{
        "version": "2.0",
        "method": "creat_user",
        "params": {
                "text": "new user",
                "email": "new_user@email.com",
                "password": "password"
        },
        "id": 1234
}
```
#### response
``` json
{
	"params": {
		"uuid":"924a6090-5b50-11e9-8be6-0a0027000001"
	},
	"id":1234,
	"jsonrpc":"2.0",
	"method":"user_create"
}

```
