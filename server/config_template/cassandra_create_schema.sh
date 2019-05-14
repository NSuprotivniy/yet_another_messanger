#!/usr/bin/env bash
mvn exec:java -Dexec.mainClass="dao.database.CassandraInit" -Dexec.args="messanger datacenter1 192.168.99.100 9042"