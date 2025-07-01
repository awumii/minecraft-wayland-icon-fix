package com.github.awumii.mixin;

import com.github.awumii.WaylandIconFixMod;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.minecraft.client.util.Window;

@Mixin(Window.class)
public class WindowMixin {
	@ModifyVariable(method = "setIcon", at = @At("STORE"), ordinal = 0)
	private int platformTrick(int original) {
		/*
			393219 = Wayland, 393220 = X11
			Minecraft checks GLFW.glfwGetPlatform(), and if it returns 393219 (Wayland), it skips setting the window icon.
			Trick this method into thinking it's running on X11, to set the window icon.
			Minecraft uses the same GLFW.glfwSetWindowIcon code for both X11 and Win32, so this implementation should be universal.
		 */
		int platform = GLFW.glfwGetPlatform();
		if (platform == GLFW.GLFW_PLATFORM_WIN32 || platform == GLFW.GLFW_PLATFORM_COCOA) return original; // Skip Windows and MacOS

		// Running on X11 - Just display a warning
		if (platform != GLFW.GLFW_PLATFORM_WAYLAND) {
			WaylandIconFixMod.LOGGER.warn("Not on Wayland: Not doing anything!");
			WaylandIconFixMod.LOGGER.warn(
					"If you are sure that you are using a Wayland desktop session, you have to install a patched GLFW first. " +
					"Make sure you actually forced the game to use the patched GLFW instead of the built-in library.");
            return original;
        }

		// Running on Wayland - perform the trickery
		if (original == 393219) {
			WaylandIconFixMod.LOGGER.info("Wayland detected: Forcing Minecraft to display the window icon.");
            return 393220;
        }
		return original;
	}
}