package ua.kiev.prog;

import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

public class JsonMessages {
    private List<Message> list;

    public JsonMessages(List<Message> sourceList, long fromIndex) {
        list = sourceList.stream()
                .filter(message -> message.getDate().toInstant(ZoneOffset.UTC).getEpochSecond() > fromIndex)
                .sorted((m1, m2) -> Long.compare(m1.getDate().toInstant(ZoneOffset.UTC).getEpochSecond(),
                                                 m2.getDate().toInstant(ZoneOffset.UTC).getEpochSecond()))
                .collect(Collectors.toList());
    }
}
