package keystrokesmod.client.module;

import java.util.ArrayList;
import java.util.List;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module.ModuleCategory;

public class GuiModuleManager {
	
	private List<GuiModule> guiModules = new ArrayList<GuiModule>();

	public GuiModuleManager() {
	      Module.ModuleCategory[] values;
	      int categoryAmount = (values = Module.ModuleCategory.values()).length;
	      for(int category = 1; category < categoryAmount; ++category) {
	         addModule(new GuiModule(values[category], values[category].getParentCategory())); 
	      }
	}
	
	public GuiModule getModuleByModuleCategory(ModuleCategory name) {
		for (GuiModule module : guiModules) {
			if (module.getGuiCategory() == name) return module;
		}
		return null;
	}
	
	public void addModule(GuiModule m) {
		guiModules.add(m);
	} 
	
	public List<GuiModule> getModules() {
		return guiModules;
	}
	
}