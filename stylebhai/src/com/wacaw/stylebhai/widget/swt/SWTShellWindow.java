package com.wacaw.stylebhai.widget.swt;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.wacaw.stylebhai.core.AbstractScreen;
import com.wacaw.stylebhai.util.Logger;
import com.wacaw.stylebhai.widget.MDIWindow;
import com.wacaw.stylebhai.widget.WidgetWrapper;

public class SWTShellWindow extends Window implements com.wacaw.stylebhai.widget.Window {
	private Class<? extends AbstractScreen> screenClass;
	private AutowireCapableBeanFactory bf;
	
	protected SWTShellWindow(Class<? extends AbstractScreen> screenClass,
			AutowireCapableBeanFactory bf) {
		super((Shell)null);
		setBlockOnOpen(true);
		this.screenClass = screenClass;
		this.bf = bf;
		this.open();
	}

	SWTWindow container;
	
	@Override
	protected Control createContents(Composite parent) {
		try {
			container = SWTWidgetUtils.createScreen(screenClass, parent, null, bf);
			this.getShell().setText(container.getScreen().getTitle());
			if (container.getScreen().getIcon() != null) {
				//this.getShell().setImage(SWTWidgetUtils.getImage(container.getScreen().getIcon()));
			}
		} catch (Exception e) {
			showMessage("Window Creation", "Error creating window", e);
		}
		return parent;
	}
	
	@Override
	public Object createDialog(Class<?> dialogClass, Object... params) {
		return null;
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
	public WidgetWrapper getWidget(String widgetName) {
		return container.getWidget(widgetName);
	}

	@Override
	public MDIWindow getParent() {
		return null;
	}

	@Override
	public Object getNativeWidget() {
		return this;
	}
	
}
