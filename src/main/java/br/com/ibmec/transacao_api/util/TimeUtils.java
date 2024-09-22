package br.com.ibmec.transacao_api.util;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeUtils {
    public static LocalDateTime getTimeBefore(long minutes, Clock clock) {
        return LocalDateTime.now(clock).minus(minutes, ChronoUnit.MINUTES);
    }
}
