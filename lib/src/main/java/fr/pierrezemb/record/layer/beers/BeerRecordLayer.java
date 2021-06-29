package fr.pierrezemb.record.layer.beers;

import beers.BeersProto;
import com.apple.foundationdb.record.RecordMetaData;
import com.apple.foundationdb.record.RecordMetaDataBuilder;
import com.apple.foundationdb.record.metadata.Index;
import com.apple.foundationdb.record.metadata.IndexTypes;
import com.apple.foundationdb.record.metadata.Key;
import com.apple.foundationdb.record.provider.foundationdb.FDBRecordContext;
import com.apple.foundationdb.record.provider.foundationdb.FDBRecordStore;
import com.apple.foundationdb.record.provider.foundationdb.keyspace.DirectoryLayerDirectory;
import com.apple.foundationdb.record.provider.foundationdb.keyspace.KeySpace;
import com.apple.foundationdb.record.provider.foundationdb.keyspace.KeySpaceDirectory;
import com.apple.foundationdb.record.provider.foundationdb.keyspace.KeySpacePath;

public class BeerRecordLayer {

  public FDBRecordStore createFDBRecordStore(FDBRecordContext context, String tenant) {
    // Create a path for the "beers-app" application's and "tenant" like this:
    //└── application
    //    └── beers-app
    //        └── tenant
    //            ├── user-1
    //            └── user-42
    KeySpace keySpace = new KeySpace(
      new DirectoryLayerDirectory("application")
        .addSubdirectory(new KeySpaceDirectory("tenant", KeySpaceDirectory.KeyType.STRING)));

    KeySpacePath path = keySpace
      .path("application", "beers-app")
      .add("tenant", tenant);

    // load protobuf
    RecordMetaDataBuilder metadataBuilder = RecordMetaData
      .newBuilder()
      .setRecords(BeersProto.getDescriptor());

    // create primary key
    metadataBuilder
      .getRecordType("Beer")
      .setPrimaryKey(Key.Expressions.field("id"));

    // create index in category
    metadataBuilder.addIndex(
      "Beer",
      new Index("beer-category-index",
        Key.Expressions.field("category"),
        IndexTypes.VALUE));

    // create index in category
    metadataBuilder.addIndex(
      "Beer",
      new Index("beer-abv-index", Key.Expressions.field("abv"), IndexTypes.VALUE));

    return FDBRecordStore
      .newBuilder()
      .setMetaDataProvider(metadataBuilder.build())
      .setContext(context)
      .setKeySpacePath(path)
      .createOrOpen();
  }
}
