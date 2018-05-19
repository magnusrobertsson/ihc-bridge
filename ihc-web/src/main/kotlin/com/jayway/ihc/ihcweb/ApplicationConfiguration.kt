package com.jayway.ihc.ihcweb

import com.jayway.ihc.ihcweb.internal.DummyIhc
import com.jayway.ihc.ihcweb.internal.SoapClientIhc
import com.jayway.ihc.ihcweb.model.DeviceRepository
import com.jayway.ihc.ihcweb.model.Ihc
import org.openhab.binding.ihc.ws.IhcClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:ihc.properties")
class ApplicationConfiguration {
    @Value("\${ihc.host}")
    private val host: String? = null

    @Value("\${ihc.port}")
    private val port: Int = 443

    @Value("\${ihc.username}")
    private val username: String? = null

    @Value("\${ihc.password}")
    private val password: String? = null

    @Bean
    fun ihcClient(): IhcClient {
        val client = IhcClient("${host}:${port}", username, password)
        //client.setProjectFile("ihc.xml");
        //client.openConnection()
        return client
    }

    @Bean
    fun ihc(): Ihc {
        //return SoapClientIhc(ihcClient())
        return DummyIhc()
    }

    @Bean
    fun deviceRepository(): DeviceRepository {
        return DeviceRepository(ihc())
    }
}