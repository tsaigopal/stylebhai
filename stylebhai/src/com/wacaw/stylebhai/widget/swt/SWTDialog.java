package com.wacaw.stylebhai.widget.swt;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.wacaw.stylebhai.core.AbstractScreen;
import com.wacaw.stylebhai.core.StylerException;
import com.wacaw.stylebhai.widget.Dialog;
import com.wacaw.stylebhai.widget.MDIWindow;
import com.wacaw.stylebhai.widget.WidgetWrapper;

public class SWTDialog extends org.eclipse.jface.dialogs.Dialog implements Dialog {
	private Class<? extends AbstractScreen> screenClass;
	private SWTWindow window;
	private MDIWindow parent;
	private AutowireCapableBeanFactory bf;
	
	private static Map<Integer, String> actions = new HashMap<Integer, String>();
	static {
		actions.put(OK, "ok");
		actions.put(CANCEL, "cancel");
	}
	
	public SWTDialog(Class<? extends AbstractScreen> screenClass, AutowireCapableBeanFactory factory) {
		this((Shell)null, screenClass, factory);
	}

	public SWTDialog(SWTMDIWindow window, Class<? extends AbstractScreen> screenClass, AutowireCapableBeanFactory factory) {
		this(window.getShell(), screenClass, factory);
	}

	private SWTDialog(Shell shell, Class<? extends AbstractScreen> screenClass, AutowireCapableBeanFactory factory) {
		super(shell);
		this.screenClass = screenClass;
		this.bf = factory;
		this.open();
	}

	@Override
	public Object getReturnValue() {
		return window.getScreen().getReturnValue();
	}
	
	@Override
	protected void buttonPressed(int buttonId) {
		window.getScreen().executeAction(actions.get(buttonId));
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		try {
			window = SWTWidgetUtils.createScreen(screenClass, parent, null, bf);
			window.getScreen().setWindowHandle(this);
			return (Control) window.getNativeWidget();
		} catch (Exception e) {
			throw new StylerException(e);
		}
	}

	@Override
	public Object createDialog(Class<?> dialogClass, Object... params) {
		return null;
	}

	@Override
	public void showMessage(String title, String message, Exception e) {
		MessageDialog.openInformation(getShell(), title, message);
	}

	@Override
	public WidgetWrapper getWidget(String widgetName) {
		return window.getWidget(widgetName);
	}

	@Override
	public MDIWindow getParent() {
		return parent;
	}

	public void setParent(MDIWindow parent) {
		this.parent = parent;
	}
	
	@Override
	public Object getNativeWidget() {
		return window.getNativeWidget();
	}
}
