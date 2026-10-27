package com.angychikngames.worldsouls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

final class NameFilter {
    private static final HexFormat HEX = HexFormat.of();
    private static final Map<Integer, Set<String>> BLOCKED = loadHashes();

    private NameFilter() {
    }

    static boolean isBlocked(String name) {
        String lowercase = name.toLowerCase(Locale.ROOT);
        StringBuilder normalized = new StringBuilder();
        char previous = 0;
        for (int i = 0; i < lowercase.length(); i++) {
            char c = lowercase.charAt(i);
            if (c == ' ') {
                break;
            }
            if (c >= 'a' && c <= 'z') {
                if (c != previous) {
                    normalized.append(c);
                }
                previous = c;
            }
        }
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is unavailable", e);
        }
        for (var entry : BLOCKED.entrySet()) {
            int length = entry.getKey();
            for (int start = 0; start + length <= normalized.length(); start++) {
                String part = normalized.substring(start, start + length);
                byte[] hash = digest.digest(part.getBytes(StandardCharsets.UTF_8));
                if (entry.getValue().contains(HEX.formatHex(hash))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static Map<Integer, Set<String>> loadHashes() {
        var stream = NameFilter.class.getResourceAsStream("/assets/worldsouls/name-filter.sha256");
        if (stream == null) {
            throw new IllegalStateException("World Souls name filter is missing");
        }
        Map<Integer, Set<String>> hashes = new HashMap<>();
        try (var reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ", 2);
                int length = Integer.parseInt(parts[0]);
                Set<String> group = hashes.get(length);
                if (group == null) {
                    group = new HashSet<>();
                    hashes.put(length, group);
                }
                group.add(parts[1]);
            }
        } catch (IOException e) {
            throw new IllegalStateException("World Souls name filter could not be read", e);
        }
        return hashes;
    }
}
