/*
 * Copyright 2017-Present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cook;

import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.Charset;

import io.pivotal.spring.cloud.service.config.PlainTextConfigClient;

import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CookController {

	private final Menu menu;

	private PlainTextConfigClient configClient;

	public CookController(Menu menu) {
		this.menu = menu;
	}

	@RequestMapping("/restaurant")
	public String restaurant() {
		return String.format("Today's special is: %s", menu.getSpecial());
	}

	@RequestMapping("/restaurant/secret-menu")
	public String secretMenu() {
		return menu.getSecretMenu();
	}

	@RequestMapping("/restaurant/dessert-menu")
	String dessertMenu() throws IOException {
		InputStream input = configClient.getConfigFile("cloud", "master", "dessert.json").getInputStream();
		return StreamUtils.copyToString(input, Charset.defaultCharset());
	}

}