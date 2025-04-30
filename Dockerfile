# Используем официальный образ Vault
FROM hashicorp/vault:1.15.2

# Копируем скрипт инициализации в контейнер
COPY vault-init/init.sh /vault-init/init.sh

# Делаем скрипт исполняемым
RUN chmod +x /vault-init/init.sh

# Устанавливаем entrypoint для запуска Vault и скрипта инициализации
ENTRYPOINT ["/bin/sh", "-c", "vault server -dev & sleep 5 && /vault-init/init.sh && tail -f /dev/null"]
