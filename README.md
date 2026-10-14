# zlmediakit-wvp-docker

Docker 一键搭建 ZLMediaKit + WVP-GB28181-pro 流媒体平台。

> **2026-10 现代化维护更新**：原 2021 版使用的基础镜像(`java:8`、`ubuntu:18.04`)均已 EOL，
> 镜像源 `github.com.cnpmjs.org` 已失效，且内置的 wvp-gb28181-2.0.0.jar 已落后上游十余个大版本。
> 本版改为直接对齐 WVP 上游官方 docker 部署方案（v2.7.4），不再本地编译。

## 使用

```bash
git clone https://github.com/ViperJooy/zlmediakit-wvp-docker.git
cd zlmediakit-wvp-docker
cd docker

# 1. 按需修改 .env：SIP 域/ID/密码、流媒体端口、服务器公网 IP 等
# 2. 启动
docker compose up -d
```

- WVP 管理界面：`http://服务器IP:8080`（由 nginx 代理，默认账号 admin / admin）
- 端口一览（均可在 `.env` 修改）：
  - `8080` Web 管理界面
  - `8160` SIP 信令（UDP/TCP）
  - `10003` RTP 收流
  - `10001` RTMP / `10002` RTSP
- 数据落盘：`docker/volumes/`（MySQL、Redis、录像）

## 首次建库

MySQL 容器首次启动会自动执行 `docker/初始化-mysql-2.7.4.sql` 完成建表。
若容器数据卷已存在旧库，先清掉 `docker/volumes/mysql/` 再启动。

## 文件说明

| 文件 | 说明 |
|---|---|
| `docker/docker-compose.yml` | 编排文件（来自 WVP 上游官方，微调见下） |
| `docker/.env` | 端口 / SIP / IP 配置 |
| `docker/初始化-mysql-2.7.4.sql` | 数据库初始化脚本（上游 2.7.4 版） |

## 与旧版的差异

- ❌ 移除本地编译 ZLMediaKit 的 `Dockerfile.runtime`（引用失效源，且 ubuntu:18.04 已 EOL）→ 改用官方镜像 `zlmediakit/zlmediakit:master`
- ❌ 移除 EOL 的 `java:8` 基础镜像和过期的 `wvp-gb28181-2.0.0.jar`、`wvp.sqlite` → 改用上游官方 wvp 容器构建
- ❌ 移除 Redis 5.0.3 → 升级为官方 compose 方案（Redis + MySQL 8 + nginx）
- ⚠️ 旧库数据不兼容，升级前请自行迁移设备配置

## 变更记录

- 2021-10 初版
- 2026-10 现代化：对齐上游 WVP v2.7.4 官方 docker 部署，修复失效镜像源与 EOL 基础镜像
