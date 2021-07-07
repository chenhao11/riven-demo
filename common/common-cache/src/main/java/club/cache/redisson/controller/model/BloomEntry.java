package club.cache.redisson.controller.model;

import lombok.Data;

/**
 * @author riven
 * @date 2021-07-07 15:00
 */
@Data
public class BloomEntry {
    private String first;

    private String second;

    public static BloomEntry of(String first, String second) {
        BloomEntry bloomEntry = new BloomEntry();
        bloomEntry.setFirst(first);
        bloomEntry.setSecond(second);
        return bloomEntry;
    }
}
