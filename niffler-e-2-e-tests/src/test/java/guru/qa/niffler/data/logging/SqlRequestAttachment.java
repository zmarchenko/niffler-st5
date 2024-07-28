package guru.qa.niffler.data.logging;

import io.qameta.allure.attachment.AttachmentData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class SqlRequestAttachment implements AttachmentData {

    private final String name;

    @Getter
    private final String sql;


    @Override
    public String getName() {
        return name;
    }
}