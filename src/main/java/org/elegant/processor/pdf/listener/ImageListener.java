package org.elegant.processor.pdf.listener;

import com.google.common.collect.Sets;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.ImageRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IEventListener;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import org.elegant.model.jooq.tables.pojos.Book;
import org.elegant.model.jooq.tables.pojos.BookCover;
import org.elegant.repository.BookCoverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public class ImageListener implements IEventListener {
    private Consumer<PdfImageXObject> consumer;

    public ImageListener(Consumer<PdfImageXObject> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void eventOccurred(IEventData data, EventType type) {
        if (data instanceof ImageRenderInfo) {
            ImageRenderInfo imageInfo = (ImageRenderInfo)data;
            PdfImageXObject image = imageInfo.getImage();

            consumer.accept(image);
        } else {
            consumer.accept(null);
        }
    }

    @Override
    public Set<EventType> getSupportedEvents() {
        return Sets.newHashSet(EventType.values());
    }
}
