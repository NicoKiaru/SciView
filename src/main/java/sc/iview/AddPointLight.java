package sc.iview;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.command.Command;

@Plugin(type = Command.class, 
		menuPath = "SciView>Add>Point Light")
public class AddPointLight implements Command {

	@Parameter
	private SciViewService sceneryService;

	@Override
	public void run() {
		sceneryService.getActiveSciView().addPointLight();
	}

}
