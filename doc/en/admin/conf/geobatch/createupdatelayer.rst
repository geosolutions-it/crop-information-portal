.. module:: cippak.admin.conf.geobatch.createupdatelayer
   :synopsis: Learn about how to configure Crop Information Portal Components.

.. _cippak.admin.conf.geobatch.createupdatelayer:

Create-Or-Update-Layer ingestion flow
=====================================

.. sourcecode:: xml

    <?xml version="1.0" encoding="UTF-8" ?>
    <FlowConfiguration>

        <id>createupdatelayer</id>
        <description>Updates or creates a layer from a given shapefile</description>
        <name>Create or update Layer</name>

        <autorun>true</autorun>

        <EventGeneratorConfiguration>
            <wildCard>*.*</wildCard>
            <watchDirectory>createupdatelayer/in</watchDirectory>
            <osType>OS_UNDEFINED</osType>
            <eventType>FILE_ADDED</eventType>
            <id>Ds2dsFlow</id>
            <keepFiles>true</keepFiles>
            <serviceID>fsEventGeneratorService</serviceID>
            <description>Ds2ds event generator description</description>
            <name>Ds2ds</name>
        </EventGeneratorConfiguration>

        <EventConsumerConfiguration>
            <id>ds2ds</id>
            <description>ds2ds</description>
            <name>ds2ds</name>
            <performBackup>false</performBackup>
            <listenerId>ConsumerLogger0</listenerId>
            <listenerId>Cumulator</listenerId>
            
            <Ds2dsConfiguration>
                <id>Ds2dsGeneratorService</id>
                <description>Ds2ds action</description>
                <name>Ds2dsConfiguration</name>

                <listenerConfigurations/>
                <listenerId>ConsumerLogger0</listenerId>
                <listenerId>Cumulator</listenerId>

                <failIgnored>false</failIgnored>
                <purgeData>true</purgeData>
                <moveData>false</moveData>

                <outputFeature>

                    <dataStore>
                        <entry>
                            <string>dbtype</string>
                            <string>postgis</string>
                        </entry>
                        <entry>
                            <string>host</string>
                            <string>localhost</string>
                        </entry>
                        <entry>
                            <string>port</string>
                            <string>5432</string>
                        </entry>
                        <entry>
                            <string>database</string>
                            <string>NRL</string>
                        </entry>
                        <entry>
                            <string>schema</string>
                            <string>public</string>
                        </entry>
                        <entry>
                            <string>user</string>
                            <string>geoserver</string>
                        </entry>
                        <entry>
                            <string>passwd</string>
                            <string>******</string>
                        </entry>
                    </dataStore>
                </outputFeature>

            </Ds2dsConfiguration>
        </EventConsumerConfiguration>

        <ListenerConfigurations>
            <LoggingProgressListener>
                <serviceID>loggingListenerService</serviceID>
                <id>ConsumerLogger0</id>
                <loggerName>it.geosolutions.ConsLogger</loggerName>
                <appendToListenerForwarder>true</appendToListenerForwarder>
            </LoggingProgressListener>

            <CumulatingProgressListener>
                <serviceID>cumulatingListenerService</serviceID>
                <id>Cumulator</id>
                <appendToListenerForwarder>true</appendToListenerForwarder>
            </CumulatingProgressListener>

            <StatusProgressListener>
                <serviceID>statusListenerService</serviceID>
                <id>Status</id>
                <appendToListenerForwarder>true</appendToListenerForwarder>
            </StatusProgressListener>
        </ListenerConfigurations>
    </FlowConfiguration>

