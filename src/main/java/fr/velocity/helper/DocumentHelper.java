package fr.velocity.helper;

import fr.velocity.annotation.Document;
import fr.velocity.document.AbstractDocument;
import fr.velocity.exception.DocumentException;
import java.util.Objects;

public final class DocumentHelper {

    private DocumentHelper() {
        // Empty contructor
    }

    public static <T extends AbstractDocument> String getIndexName(T object) {
        return validateDocumentAnnotation(object).getAnnotation(Document.class).indexName();
    }

    public static <T extends AbstractDocument> String getAlias(T object) {
        return validateDocumentAnnotation(object).getAnnotation(Document.class).alias();
    }

    public static <T extends AbstractDocument> String getAliasRead(T object) {
        return validateDocumentAnnotation(object).getAnnotation(Document.class).aliasRead();
    }

    public static <T extends AbstractDocument> String getAliasWrite(T object) {
        return validateDocumentAnnotation(object).getAnnotation(Document.class).aliasWrite();
    }

    public static <T extends AbstractDocument> String getType(T object) {
        return validateDocumentAnnotation(object).getAnnotation(Document.class).type();
    }

    private static <T extends AbstractDocument> Class<? extends AbstractDocument> validateDocumentAnnotation(
            T object) {
        if (Objects.isNull(object)) {
            throw new DocumentException("The document to get is null");
        }
        Class<? extends AbstractDocument> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(Document.class)) {
            throw new DocumentException("The class " + clazz.getSimpleName() + " is not annotated with Document");
        }
        return clazz;
    }

}
