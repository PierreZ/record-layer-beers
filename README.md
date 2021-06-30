# record-layer-beers

An Hello, world of Record-Layer with some beers 🍺🍺

## How-to

1. [Download FDB client package](https://apple.github.io/foundationdb/downloads.html)
2. Install Docker (used to boostrap a FoundationDB container)
3. Run `gradle test`

## Entrypoints

* Protobuf definition is [here](lib/src/main/proto/beers.proto)
* creation of the [FDBRecordStore](https://javadoc.io/static/org.foundationdb/fdb-record-layer-core/2.10.164.0/com/apple/foundationdb/record/provider/foundationdb/FDBRecordStore.html) is done [here](lib/src/main/java/fr/pierrezemb/record/layer/beers/BeerRecordLayer.java)
* usage of the [FDBRecordStore](https://javadoc.io/static/org.foundationdb/fdb-record-layer-core/2.10.164.0/com/apple/foundationdb/record/provider/foundationdb/FDBRecordStore.html) is done [here](lib/src/test/java/fr/pierrezemb/record/layer/beers/BeersTest.java)
