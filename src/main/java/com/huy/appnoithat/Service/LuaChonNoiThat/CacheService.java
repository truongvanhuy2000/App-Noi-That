package com.huy.appnoithat.Service.LuaChonNoiThat;

import java.util.List;
import java.util.Optional;

public interface CacheService {
    String createUniqueId(String... param);

    <X> void writeCache(List<X> data, String uniqueId);

    <C> Optional<List<C>> readCache(String uniqueId, Class<C> objectType);

    boolean isContain(String uniqueId);

    void clearCache();
}
