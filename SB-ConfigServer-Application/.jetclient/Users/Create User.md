```toml
name = 'Create User'
method = 'POST'
url = 'http://localhost:8080/api/users'
sortWeight = 1000000
id = '8693548e-0ea2-4221-8755-e4f916a8d182'

[body]
type = 'JSON'
raw = '''
{
  "firstName" : "John",
  "lastName" : "Doe",
  "email" : "john@mail.com",
  "password" : "123",
  "roleId" : 1
}'''
```
