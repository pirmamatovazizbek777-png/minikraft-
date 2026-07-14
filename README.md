# MiniKraft Plugin 🎮

**Minecraft 1.20 Paper Server Plugin** - Bitta katta Java kod faylda!

## ✨ Funksiyalar

| Buyruq | Tavsifi | Ruxsat |
|--------|---------|--------|
| `/tp <player>` | Boshqa o'yunchiga o'tish | Hamma |
| `/home` | O'zing belgilagan uyga qaytish | Hamma |
| `/sethome` | Hozirgi joylashni uy qilish | Hamma |
| `/spawn` | Server spawn nuqtasiga o'tish | Hamma |
| `/setspawn` | Spawn nuqtasini belgilash | Admin |

## 🚀 O'rnatish

### Talablar
- Java 17+
- Paper Server 1.20.1
- Maven

### Build qilish
```bash
mvn clean package
```

### Plugin o'rnatish
1. `target/minikraft-1.0.0.jar` faylni ko'chiring
2. Server-ning `plugins/` papkasiga joylashtiring
3. Server-ni qayta ishga tushiring

## 📝 Kod Tuzilishi

**Bitta faylda:**
- ✅ Main Plugin Class
- ✅ Hamma Command Handlers
- ✅ Event Listeners
- ✅ Home Manager
- ✅ Spawn Manager

## 🎯 Buyruq Misollari

```
/sethome           → Hozirgi joylashni uy qilish
/home              → Uyga qaytish
/tp Azizbek_777_01 → Azizbek_777_01 ga o'tish
/spawn             → Spawn-ga o'tish
/setspawn          → Spawn belgilash (Admin)
```

## 🔧 Tekhnologiya

- **Language:** Java 17
- **Framework:** Paper API 1.20.1
- **Build Tool:** Maven
- **Dependency:** BungeeCord Chat API

## 👤 Muallif

**Azizbek_777_01**

## 📄 Litsenziya

MIT License

---

**Kod yangilandi:** 2026-07-14
**Plugin versiyasi:** 1.0.0
**Minecraft versiyasi:** 1.20+

**Enjoyyy! 🎉**
