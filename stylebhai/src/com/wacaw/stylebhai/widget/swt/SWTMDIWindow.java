package com.wacaw.stylebhai.widget.swt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.wacaw.stylebhai.config.WidgetConfig;
import com.wacaw.stylebhai.core.AbstractScreen;
import com.wacaw.stylebhai.util.Logger;
import com.wacaw.stylebhai.widget.MDIWindow;
import com.wacaw.stylebhai.widget.WidgetBuilder;
import com.wacaw.stylebhai.widget.WidgetWrapper;
import com.wacaw.stylebhai.widget.Window;

public class SWTMDIWindow extends ApplicationWindow implements MDIWindow {
	private List<Window> windows = new ArrayList<>();

	private WidgetConfig screenConfig;

	private CTabFolder tablFolder;

	private AutowireCapableBeanFactory bf;
	
	public SWTMDIWindow(Class<? extends AbstractScreen> screenClass, AutowireCapableBeanFactory bf) {
		super(null);
		this.bf = bf;
		screenConfig = WidgetBuilder.getConfig(screenClass);
		addToolBar(SWT.FLAT | SWT.HORIZONTAL);
		addMenuBar();
//		buildToolbar(screenConfig.getChild("ToolBar"));
		buildMenubar(screenConfig.getChild("MenuBar"));
		setBlockOnOpen(true);
		open();
	}
	
	protected Control createContents(Composite parent) {
		SWTWidgetUtils.applyStyle(getShell(), screenConfig.getChild("Shell").getProperties());
//		parent.setLayout(new FillLayout());
		SashForm container = new SashForm(parent, SWT.HORIZONTAL);
		container.setLayout(new FillLayout());
		tablFolder = new CTabFolder(container, SWT.BORDER);
		tablFolder.setSize(500, 100);
		Composite c = new Composite(container, SWT.NONE);
		//TODO get root screen
		container.setWeights(new int[]{80,20});
		
		return container;
	}
	
	protected void buildToolbar(WidgetConfig toolBarConfig) {
		if (toolBarConfig == null) {
			return;
		}
		for (WidgetConfig config : toolBarConfig.getChildren()) {
			getToolBarManager().add(createAction(config.getProperties()));
		}
	}

	protected void buildMenubar(WidgetConfig menuConfig) {
		if (menuConfig == null) {
			return;
		}
		for (WidgetConfig config: menuConfig.getChildren()) {
			createAction(getMenuBarManager(), config);
		}
	}
	
	private void createAction(IContributionManager manager, WidgetConfig config) {
		if (config.getChildren().size() > 0) {
			MenuManager subMenu = new MenuManager(config.getProperties().get("text"));
			manager.add(subMenu);
			for (WidgetConfig childConfig : config.getChildren()) {
				createAction(subMenu, childConfig);
			}
		} else {
			manager.add(createAction(config.getProperties()));
		}
	}

	protected Action createAction(final Map<String, String> menuProps) {
		return new Action() {
			{
				setText(menuProps.get("text"));
				setDescription(menuProps.get("description"));
				if (menuProps.get("image") != null) {
					setImageDescriptor(SWTWidgetUtils.getImage(menuProps.get("image")));
				}
//				setAccelerator(SWT.);
			}
			@Override
			public void run() {
				try {
					openChild((Class<? extends AbstractScreen>) Class.forName(menuProps.get("class")));
				} catch (Exception e) {
					showMessage("Error opening Child", e.getMessage(), e);
				}
			}
		};
	}

	@Override
	public Window openChild(Class<? extends AbstractScreen> screenClass, Object... params) throws Exception {
		CTabItem tabItem = createTab();
		Composite composite = (Composite)tabItem.getControl();
		SWTWindow window = SWTWidgetUtils.createScreen(screenClass, composite, params, bf);
		window.setParent(this);
		AbstractScreen screenObject = window.getScreen();
		if (screenObject.getIcon() != null) {
			tabItem.setImage(new Image(tablFolder.getDisplay(), 
					this.getClass().getResourceAsStream("/images/"+screenObject.getIcon())));
		}
		tabItem.setText(screenObject.getTitle());
		
		windows.add(window);
		return window;
	}

	public CTabItem createTab() throws Exception {
		CTabItem tabItem = new CTabItem(tablFolder, SWT.CLOSE);
		Composite c = new Composite(tablFolder, SWT.NONE);
		c.setLayout(new FillLayout());
		tabItem.setControl(c);
		tablFolder.setSelection(tabItem);
		return tabItem;
	}
	
	@Override
	public void showMessage(String title, String message, Exception e) {
		if (e != null) {
			Logger.log("Exception occured", e);
			MessageDialog.openError(getShell(), title, message);
		} else {
			MessageDialog.openInformation(getShell(), title, message);
		}
	}
	
	@Override
	public void closeChild(Window window) {
		windows.remove(window);
		closeChild1((Composite) window.getNativeWidget());
	}
	
	public void closeChild1(Composite child) {
		for (int i=0; i<tablFolder.getItemCount(); i++) {
			Composite tab = (Composite) tablFolder.getItem(i).getControl();
			if (tab.getChildren()[0] == child) {
				tab.getParent().dispose();
			}
		}
	}

	@Override
	public Object getNativeWidget() {
		return getShell();
	}

	@Override
	public Object createDialog(Class<?> dialogClass, Object ... params) {
		return null;//SWTWidgetUtils.createDialog(mainWindow.getShell(), dialogClass, params);
	}

	@Override
	public WidgetWrapper getWidget(String widgetName) {
		return null;
	}

	@Override
	public MDIWindow getParent() {
		return null;
	}
	
	@Override
	public boolean close() {
		return super.close();
	}
}
