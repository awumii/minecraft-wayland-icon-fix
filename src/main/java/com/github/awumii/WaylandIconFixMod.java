package com.github.awumii;

import net.fabricmc.api.ClientModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaylandIconFixMod implements ClientModInitializer {
	public static final String MOD_ID = "waylandiconfix";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		// what to do here? Its just a simple mixin.
	}
}