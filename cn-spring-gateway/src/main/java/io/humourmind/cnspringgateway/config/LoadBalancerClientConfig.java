package io.humourmind.cnspringgateway.config;

import java.util.List;
import java.util.stream.Collectors;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerList;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(RibbonAutoConfiguration.class)
public class LoadBalancerClientConfig {

	private final DiscoveryClient discoveryClient;

	public LoadBalancerClientConfig(DiscoveryClient discoveryClient) {
		this.discoveryClient = discoveryClient;
	}

	@Bean
	public ServerList<?> ribbonServerList(IClientConfig config) {
		CFServerList cfServerList = new CFServerList(discoveryClient);
		cfServerList.initWithNiwsConfig(config);
		return cfServerList;
	}

	class CFServerList extends AbstractServerList<Server> {

		private IClientConfig clientConfig;

		private final DiscoveryClient discoveryClient;

		public CFServerList(DiscoveryClient discoveryClient) {
			this.discoveryClient = discoveryClient;
		}

		@Override
		public void initWithNiwsConfig(IClientConfig clientConfig) {
			this.clientConfig = clientConfig;
		}

		@Override
		public List<Server> getInitialListOfServers() {
			return cloudFoundryServers();
		}

		@Override
		public List<Server> getUpdatedListOfServers() {
			return cloudFoundryServers();
		}

		private List<Server> cloudFoundryServers() {
			return discoveryClient.getInstances(clientConfig.getClientName())
					.stream().map(i -> new Server(i.getHost(), i.getPort()))
					.collect(Collectors.toList());
		}
	}

}
