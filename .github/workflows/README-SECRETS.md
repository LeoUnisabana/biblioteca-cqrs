# Configuración de Secretos para CI/CD

Para que el workflow de GitHub Actions funcione correctamente, necesitas configurar los siguientes secretos en tu repositorio.

## 📋 Secretos Requeridos

Ve a: **Repositorio → Settings → Secrets and variables → Actions → New repository secret**

### 1. DOCKER_USERNAME
- **Descripción**: Tu nombre de usuario de Docker Hub
- **Valor**: `leounisabana`
- **Uso**: Autenticación en Docker Hub para subir imágenes

### 2. DOCKER_PASSWORD
- **Descripción**: Tu contraseña o Access Token de Docker Hub
- **Valor**: Tu contraseña de Docker Hub (recomendado: usar Access Token)
- **Cómo obtener un Access Token**:
  1. Ve a https://hub.docker.com/settings/security
  2. Click en "New Access Token"
  3. Dale un nombre descriptivo (ej: "GitHub Actions CI/CD")
  4. Copia el token generado
  5. Úsalo como valor del secreto

### 3. GH_PAT
- **Descripción**: GitHub Personal Access Token para actualizar el repo de deploy
- **Valor**: Token con permisos `repo` (acceso completo a repositorios privados)
- **Cómo crear el PAT**:
  1. Ve a GitHub → Settings → Developer settings → Personal access tokens → Tokens (classic)
  2. Click en "Generate new token (classic)"
  3. Nombre: "CI/CD Cross-Repo Update"
  4. Expiration: 90 days o lo que prefieras
  5. Selecciona el scope: ✅ **repo** (Full control of private repositories)
  6. Genera el token y cópialo inmediatamente
  7. Úsalo como valor del secreto

## 🔍 Verificar Configuración

Una vez configurados los secretos, puedes verificar que están disponibles en:
```
Repositorio → Settings → Secrets and variables → Actions
```

Deberías ver:
- ✅ DOCKER_USERNAME
- ✅ DOCKER_PASSWORD  
- ✅ GH_PAT

## 🚀 Flujo del Pipeline

1. **Trigger**: Push a `main` o ejecución manual
2. **Build**: Compila el proyecto Maven
3. **Test**: Ejecuta los tests unitarios
4. **Docker**: Construye y sube imagen con tag versionado
5. **Update**: Actualiza `values-dev.yaml` en repo de deploy
6. **ArgoCD**: Detecta el cambio y despliega automáticamente

## 📝 Variables de Entorno del Workflow

Estas están definidas en el workflow y NO necesitan configurarse como secretos:

- `DOCKER_IMAGE`: `leounisabana/biblioteca-cqrs`
- `DEPLOY_REPO`: `LeoUnisabana/biblioteca-cqrs-deploy`
- `DEPLOY_VALUES_FILE`: `helm/biblioteca-chart/values-dev.yaml`

Si necesitas cambiar estos valores, edita el archivo `ci-cd.yml`.

## ⚠️ Seguridad

- ❌ **NUNCA** commitees secretos en el código
- ✅ Usa siempre los Secrets de GitHub
- 🔄 Rota los tokens periódicamente
- 🔒 Usa Access Tokens en lugar de contraseñas cuando sea posible.
