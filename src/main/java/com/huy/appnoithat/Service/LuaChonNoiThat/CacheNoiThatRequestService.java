package com.huy.appnoithat.Service.LuaChonNoiThat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
public class CacheNoiThatRequestService implements CacheService {
    private final static Logger LOGGER = LogManager.getLogger(CacheNoiThatRequestService.class);
    private final HashMap<String, CachedObject> cache = new HashMap<>();
    private final long expirationDuration = Duration.ofMinutes(10).toMillis();

    @Override
    public String createUniqueId(String... param) {
        String seed = "";
        for (String s : param) {
            seed = seed.concat(s);
        }
        UUID uuid = UUID.nameUUIDFromBytes(seed.getBytes());
        return uuid.toString();
    }

    @Override
    public <X> void writeCache(List<X> data, String uniqueId) {
        if (data == null || data.isEmpty()) {
            LOGGER.info("Empty data, not catching");
            return;
        }
        CachedObject cachedObject = CachedObject.builder()
                .object(data)
                .expiredTime(System.currentTimeMillis() + expirationDuration)
                .build();
        cache.put(uniqueId, cachedObject);
    }

    @Override
    public <C> Optional<List<C>> readCache(String uniqueId, Class<C> objectType) {
        CachedObject cachedObject = cache.get(uniqueId);
        if (cachedObject == null) {
            return Optional.empty();
        }
        if (isExpired(cachedObject)) {
            LOGGER.info("Cache expired");
            return Optional.empty();
        }
        try {
            return Optional.of((List<C>) cachedObject.getObject());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public boolean isContain(String uniqueId) {
        if (!cache.containsKey(uniqueId)) {
            return false;
        }
        CachedObject cachedObject = cache.get(uniqueId);
        if (cachedObject == null) {
            return false;
        }
        if (isExpired(cachedObject)) {
            LOGGER.debug("Cache expired");
            return false;
        }
        return true;
    }

    @Override
    public void clearCache() {
        cache.clear();
    }

    private boolean isExpired(CachedObject cachedObject) {
        return cachedObject.getExpiredTime() < System.currentTimeMillis();
    }

    @Builder
    @Getter
    @AllArgsConstructor
    private static class CachedObject {
        private final Object object;
        private final Long expiredTime;
    }
}
