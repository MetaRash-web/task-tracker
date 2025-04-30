echo "⏳ Waiting for Vault to start..."
sleep 3

export VAULT_ADDR='http://127.0.0.1:8200'
export VAULT_TOKEN='root'

vault kv put secret/backend jwt.secret=49ee93ddd9ebb0bb6c249af667b3d7980774a9e95920c7cf94217fa20d19398b80a94672be96820ba38af1e6c90909042b4744c0486d9020f4cec6f2817ae975

echo "✅ JWT Secret written to Vault."