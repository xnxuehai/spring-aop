package com.park.cache.interceptor;

import org.springframework.cache.interceptor.CacheOperation;
import org.springframework.cache.interceptor.CacheableOperation;
import org.springframework.lang.Nullable;

/**
 * @author Aaron
 * @date 2020/6/8 11:22 上午
 */
public class AddCacheOperation extends CacheOperation {
    @Nullable
    private final String[] field;
    @Nullable
    private final String unless;

    private final boolean sync;

    /**
     * Create a new {@link CacheableOperation} instance from the given builder.
     *
     * @since 4.3
     */
    public AddCacheOperation(Builder b) {
        super(b);
        this.field = b.field;
        this.unless = b.unless;
        this.sync = b.sync;
    }


    @Nullable
    public String[] getField() {
        return this.field;
    }

    @Nullable
    public String getUnless() {
        return this.unless;
    }

    public boolean isSync() {
        return this.sync;
    }


    /**
     * A builder that can be used to create a {@link CacheableOperation}.
     *
     * @since 4.3
     */
    public static class Builder extends CacheOperation.Builder {

        @Nullable
        private String[] field;

        @Nullable
        private String unless;

        private boolean sync;

        public void setUnless(String unless) {
            this.unless = unless;
        }

        public void setSync(boolean sync) {
            this.sync = sync;
        }

        public void setField(String[] field) {
            this.field = field;
        }

        @Override
        protected StringBuilder getOperationDescription() {
            StringBuilder sb = super.getOperationDescription();
            sb.append(" | field='");
            sb.append(this.field);
            sb.append("'");
            sb.append(" | sync='");
            sb.append(this.sync);
            sb.append("'");
            return sb;
        }

        @Override
        public AddCacheOperation build() {
            return new AddCacheOperation(this);
        }
    }

}
