package game;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;

public class PositionProperty implements Observable, Property<Position>, ReadOnlyProperty<Position>,
		ObservableValue<Position>, WritableValue<Position> {

	private Position pos;
	private Object bean;
	private String name;

	public PositionProperty() {
		this.pos = null;
		this.bean = null;
		this.name = null;
	}

	public PositionProperty(int initialPosition) {
		pos = new Position(initialPosition);
		this.bean = null;
		this.name = null;
	}

	public PositionProperty(Object bean, String name) {
		this.pos = null;
		this.bean = bean;
		this.name = name;
	}

	public PositionProperty(Object bean, String name, int initialPosition) {
		this.bean = bean;
		this.name = name;
		pos = new Position(initialPosition);
	}

	@Override
	public Object getBean() {
		return bean;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void addListener(ChangeListener<? super Position> arg0) {

	}

	@Override
	public Position getValue() {
		return pos;
	}

	@Override
	public void removeListener(ChangeListener<? super Position> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addListener(InvalidationListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeListener(InvalidationListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setValue(Position arg0) {
		pos = arg0;
	}

	@Override
	public void bind(ObservableValue<? extends Position> arg0) {
		this.pos = arg0.getValue();
	}

	@Override
	public void bindBidirectional(Property<Position> other) {
		this.pos = other.getValue();
	}

	@Override
	public boolean isBound() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unbind() {
		// TODO Auto-generated method stub

	}

	@Override
	public void unbindBidirectional(Property<Position> arg0) {
		// TODO Auto-generated method stub

	}

}
