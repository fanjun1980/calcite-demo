package com.fanjun1980.calcite.stream;

import com.fanjun1980.calcite.InMemoryTable;
import com.fanjun1980.calcite.storage.Storage;
import org.apache.calcite.schema.StreamableTable;
import org.apache.calcite.schema.Table;

public class InMemoryStreamTable extends InMemoryTable implements StreamableTable {
    public InMemoryStreamTable(String name, Storage.DummyTable it) {
        super(name, it);
    }

    @Override
    public Table stream() {
        System.out.println("streaming .....");
        return this;
    }
}
