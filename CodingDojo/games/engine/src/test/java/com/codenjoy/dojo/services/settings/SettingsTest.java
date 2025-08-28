package com.codenjoy.dojo.services.settings;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

import static com.codenjoy.dojo.client.Utils.split;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SettingsTest {

    private Settings settings;

    @Before
    public void setup() {
        settings = new SettingsImpl();
    }

    @Test
    public void performance() {
        // about 2.8 sec
        int ticks = 1_000_000;

        // given
        List<Object> options = Arrays.asList("option1", "option2");
        for (int count = 0; count < ticks; count++) {
            settings.addEditBox("edit" + count).type(Integer.class);
            settings.addSelect("select" + count, options).type(String.class);
            settings.addCheckBox("check" + count).type(Boolean.class);

            settings.parameter("edit0");
            settings.getParameter("non-exists", () -> null);
            settings.getParameters();
        }
    }

    @Test
    public void shouldGetAllOptionsContainsCreatedParameter() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class);
        Parameter<Integer> select = settings.addSelect("select", Arrays.asList("option1")).type(Integer.class);
        Parameter<Integer> check = settings.addCheckBox("check").type(Integer.class);

        // when
        List<Parameter> options = settings.getParameters();

        // then
        assertEquals(true, options.contains(edit));
        assertEquals(true, options.contains(select));
        assertEquals(true, options.contains(check));
    }

    @Test
    public void shouldUpdatePrevious_ifPresent() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class);

        // when
        Parameter<Integer> edit2 = settings.addEditBox("edit").type(Integer.class);

        // then
        assertSame(edit, edit2);

        List<Parameter> options = settings.getParameters();
        assertEquals(1, options.size());
    }

    @Test
    public void shouldGetParameterByName_returnsSameParameter() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class).def(5);

        // when
        assertSame(edit, settings.parameter("edit"));

        // then
        edit.update(12);
        assertEquals(12, edit.getValue().intValue());
    }

    @Test
    public void shouldChangedValue_whenChangeIt() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class);
        Parameter<String> select = settings.addSelect("select", Arrays.asList("option1", "option2")).type(String.class);
        Parameter<Boolean> check = settings.addCheckBox("check");

        // when
        edit.update(12);
        assertEquals(12, edit.getValue().intValue());

        // when then
        select.select(1);
        assertEquals("option2", select.getValue());

        // when then
        check.update(true);
        assertEquals(true, check.getValue());
    }

    @Test
    public void shouldGetValueType_caseSetType() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class).def(24);
        Parameter<String> select = settings.addSelect("select", Arrays.asList("option1", "option2")).type(String.class).def("option1");
        Parameter<Boolean> check = settings.addCheckBox("check").type(Boolean.class).def(true);
        Parameter<String> simple = new SimpleParameter<>("value");

        // when then
        assertEquals(Integer.class, edit.getValueType());
        assertEquals(String.class, select.getValueType());
        assertEquals(Boolean.class, check.getValueType());
        assertEquals(String.class, simple.getValueType());
    }

    @Test
    public void shouldGetValueType_caseNotSetType() {
        // given
        Parameter<Object> edit = (Parameter<Object>) settings.addEditBox("edit");
        edit.update(23);

        Parameter<Object> select = (Parameter<Object>) settings.addSelect("select", Arrays.asList("option1", "option2"));
        select.update("option1");

        Parameter<Boolean> check = settings.addCheckBox("check");
        check.update(true);

        Parameter<Object> simple = new SimpleParameter<>("value");
        simple.update("new");

        // when then
        assertEquals(Integer.class, edit.getValueType());
        assertEquals(String.class, select.getValueType());
        assertEquals(Boolean.class, check.getValueType());
        assertEquals(String.class, simple.getValueType());
    }

    @Test
    public void shouldGetValueType_caseNotSetType_caseNullValues_withoutDefault() {
        // given
        Parameter<Object> edit = (Parameter<Object>) settings.addEditBox("edit");
        edit.update(null);

        Parameter<Object> select = (Parameter<Object>) settings.addSelect("select", Arrays.asList(new String(), null));
        select.update(null);

        Parameter<Boolean> check = settings.addCheckBox("check");
        check.update(null);

        Parameter<Object> simple = new SimpleParameter<>("value");
        simple.update(null);

        // when then
        assertEquals(Object.class, edit.getValueType());
        assertEquals(String.class, select.getValueType());
        assertEquals(Boolean.class, check.getValueType());
        assertEquals(Object.class, simple.getValueType());
    }

    @Test
    public void shouldGetValueType_caseNotSetType_caseNullValues_withDefault() {
        // given
        Parameter<Object> edit = (Parameter<Object>) settings.addEditBox("edit");
        edit.def("string").update(null);

        Parameter<Object> select = (Parameter<Object>) settings.addSelect("select", Arrays.asList("option", null));
        select.def("option").update(null);

        Parameter<Boolean> check = settings.addCheckBox("check");
        check.def(true).update(null);

        // when then
        assertEquals(String.class, edit.getValueType());
        assertEquals(String.class, select.getValueType());
        assertEquals(Boolean.class, check.getValueType());
    }

    @Test
    public void shouldGetValueType_caseSelectBoxWithoutOptions() {
        // given
        SelectBox<?> select1 = settings.addSelect("select1", Arrays.asList());
        SelectBox<?> select2 = settings.addSelect("select2", new LinkedList<Object>(){{ add(null); }});

        // when then
        assertEquals(Object.class, select1.getValueType());
        assertEquals(Object.class, select2.getValueType());
    }

    @Test
    public void shouldGetDefaultValue_whenSetIt() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class).def(24);
        Parameter<String> select = settings.addSelect("select", Arrays.asList("option1", "option2")).type(String.class).def("option1");
        Parameter<Boolean> check = settings.addCheckBox("check").def(true);
        Parameter<String> simple = new SimpleParameter<>("value");

        // when then
        assertEquals(24, edit.getDefault().intValue());
        assertEquals("option1", select.getDefault());
        assertEquals(true, check.getDefault());
        assertEquals("value", simple.getDefault());
    }

    @Test
    public void shouldGetDefaultValue_whenNotSetIt() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class);
        Parameter<String> select = settings.addSelect("select", Arrays.asList("option1", "option2")).type(String.class);
        Parameter<Boolean> check = settings.addCheckBox("check");
        Parameter<String> simple = new SimpleParameter<>("value");

        // when then
        assertEquals(null, edit.getDefault());
        assertEquals(null, select.getDefault());
        assertEquals(null, check.getDefault());
        assertEquals("value", simple.getDefault());
    }

    @Test
    public void shouldHasParameter() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class).def(5);
        Parameter<String> select = settings.addSelect("select", Arrays.asList("option1", "option2", "option3")).type(String.class).def("option3");
        Parameter<Boolean> check = settings.addCheckBox("check").def(true);

        // when then
        assertEquals(true, settings.hasParameter("edit"));
        assertEquals(true, settings.hasParameter("select"));
        assertEquals(true, settings.hasParameter("check"));

        assertEquals(false, settings.hasParameter("not-exists"));
    }

    @Test
    public void shouldHasParameterPrefix() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class).def(5);
        Parameter<String> select = settings.addSelect("select", Arrays.asList("option1", "option2", "option3")).type(String.class).def("option3");
        Parameter<Boolean> check = settings.addCheckBox("check").def(true);

        // when then
        assertEquals(true, settings.hasParameterPrefix("edit"));
        assertEquals(true, settings.hasParameterPrefix("ed"));
        assertEquals(true, settings.hasParameterPrefix("select"));
        assertEquals(true, settings.hasParameterPrefix("sel"));
        assertEquals(true, settings.hasParameterPrefix("check"));
        assertEquals(true, settings.hasParameterPrefix("che"));

        assertEquals(false, settings.hasParameterPrefix("not-exists"));
        assertEquals(false, settings.hasParameterPrefix("Edit"));
        assertEquals(false, settings.hasParameterPrefix("Ed"));
        assertEquals(false, settings.hasParameterPrefix("Check"));
        assertEquals(false, settings.hasParameterPrefix("Che"));
    }

    @Test
    public void shouldCanSetDefaultValue() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class);
        Parameter<String> select = settings.addSelect("select", Arrays.asList("option1", "option2", "option3")).type(String.class);
        Parameter<Boolean> check = settings.addCheckBox("check");

        // when
        edit.def(5);
        select.def("option3");
        check.def(true);

        // then
        assertEquals(5, edit.getValue().intValue());
        assertEquals("option3", select.getValue());
        assertEquals(true, check.getValue());

        // when then
        edit.update(12);
        assertEquals(12, edit.getValue().intValue());

        // when then
        select.select(1);
        assertEquals("option2", select.getValue());

        // when then
        select.update("option1");
        assertEquals("option1", select.getValue());

        // when then
        check.update(false);
        assertEquals(false, check.getValue());

        // when then
        check.select(1);
        assertEquals(true, check.getValue());
    }

    @Test
    public void shouldCanSetDefaultValue_whenNotInSelectOptionsList() {
        // given
        Parameter<String> select = settings.addSelect("select", Arrays.asList("option1", "option2", "option3")).type(String.class);

        try {
            // when
            select.def("newValue");
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("No option 'newValue' in set [option1, option2, option3]", e.getMessage());
        }

        // then
        assertEquals(null, select.getDefault());
    }

    @Test
    public void shouldDefaultValueIsNull_whenNotSet() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class);
        Parameter<String> select = settings.addSelect("select", Arrays.asList("option1", "option2", "option3")).type(String.class);
        Parameter<Boolean> check = settings.addCheckBox("check");

        // when not
        // edit.def(5);
        // select.def("option3");
        // check.def(true);

        // then
        assertEquals(null, edit.getValue());
        assertEquals(null, select.getValue());
        assertEquals(null, check.getValue());
    }

    @Test
    public void shouldGetType() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class);
        Parameter<String> select = settings.addSelect("select", Arrays.asList("option1", "option2", "option3")).type(String.class);
        Parameter<Boolean> check = settings.addCheckBox("check");

        // when then
        assertEquals("editbox", edit.getType());
        assertEquals("selectbox", select.getType());
        assertEquals("checkbox", check.getType());
    }

    @Test
    public void shouldEditBox_canBeMultiline() {
        // given when
        EditBox<String> edit1 = settings.addEditBox("edit1").type(String.class).multiline();
        EditBox<String> edit2 = settings.addEditBox("edit2").type(String.class).multiline(true);
        EditBox<String> edit3 = settings.addEditBox("edit3").type(String.class).multiline(false);
        EditBox<String> edit4 = settings.addEditBox("edit4").type(String.class);

        // then
        assertEquals(true, edit1.isMultiline());
        assertEquals(true, edit2.isMultiline());
        assertEquals(false, edit3.isMultiline());
        assertEquals(false, edit4.isMultiline());

        // when
        edit1.multiline(false);
        edit2.multiline(false);
        edit3.multiline(true);
        edit4.multiline(true);

        // then
        assertEquals(false, edit1.isMultiline());
        assertEquals(false, edit2.isMultiline());
        assertEquals(true, edit3.isMultiline());
        assertEquals(true, edit4.isMultiline());
    }

    @Test
    public void shouldGetOptions() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class).def(42);
        Parameter<String> select = settings.addSelect("select", Arrays.asList("option1", "option2", "option3")).type(String.class).def("option2");
        Parameter<Boolean> check = settings.addCheckBox("check").def(true);

        // then
        assertEquals("[42]", edit.getOptions().toString());
        assertEquals("[option1, option2, option3]", select.getOptions().toString());
        assertEquals("[true]", check.getOptions().toString()); // TODO хорошо бы тут чтобы были все варианты

        // when
        edit.update(12);
        select.update("option1");
        check.update(false);

        // then
        assertEquals("[42, 12]", edit.getOptions().toString());
        assertEquals("[option1, option2, option3]", select.getOptions().toString());
        assertEquals("[true, false]", check.getOptions().toString());

        // when set default
        edit.update(42);
        select.update("option2");
        check.update(true);

        // when then
        assertEquals("[42]", edit.getOptions().toString());
        assertEquals("[option1, option2, option3]", select.getOptions().toString());
        assertEquals("[true]", check.getOptions().toString());

    }

    @Test
    public void shouldSetFlagChanged_whenChangeSomething() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class);
        Parameter<String> select = settings.addSelect("select", Arrays.asList("option1", "option2", "option3")).type(String.class);
        Parameter<Boolean> check = settings.addCheckBox("check");

        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());

        // when then
        edit.update(1);
        assertEquals(true, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(true, settings.changed());

        // when then
        settings.changesReacted();
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());

        // when then
        select.update("option1");
        assertEquals(false, edit.changed());
        assertEquals(true, select.changed());
        assertEquals(false, check.changed());
        assertEquals(true, settings.changed());

        // when then
        settings.changesReacted();
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());

        // when then
        check.update(true);
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(true, check.changed());
        assertEquals(true, settings.changed());

        // when then
        settings.changesReacted();
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());
    }

    @Test
    public void shouldCallOnChangeListener_whenChangeSomething_withoutDefaultValue() {
        // given
        BiConsumer<Integer, Integer> editListener = mock(BiConsumer.class);
        BiConsumer<Integer, Integer> selectListener = mock(BiConsumer.class);
        BiConsumer<Boolean, Boolean> checkListener = mock(BiConsumer.class);

        Parameter<Integer> edit = settings.addEditBox("edit")
                .type(Integer.class)
                .onChange(editListener);

        assertEquals(null, edit.getValue());

        Parameter<String> select = settings.addSelect("select",
                Arrays.asList("option1", "option2", "option3"))
                .type(String.class)
                .onChange(selectListener);

        assertEquals(null, select.getValue());

        Parameter<Boolean> check = settings.addCheckBox("check")
                .onChange(checkListener);

        assertEquals(null, check.getValue());

        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());

        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        edit.update(1);

        assertEquals(true, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(true, settings.changed());

        verify(editListener).accept(null, 1);
        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        settings.changesReacted();
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());

        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        select.update("option1");
        assertEquals(false, edit.changed());
        assertEquals(true, select.changed());
        assertEquals(false, check.changed());
        assertEquals(true, settings.changed());

        verify(selectListener).accept(null, 0);
        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        settings.changesReacted();
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());

        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        check.update(true);
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(true, check.changed());
        assertEquals(true, settings.changed());

        verify(checkListener).accept(null, true);
        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        settings.changesReacted();
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());

        verifyNoMoreInteractions(editListener, selectListener, checkListener);
    }

    @Test
    public void shouldCallOnChangeListener_whenChangeSomething_withDefaultValue() {
        // given
        BiConsumer<Integer, Integer> editListener = mock(BiConsumer.class);
        BiConsumer<Integer, Integer> selectListener = mock(BiConsumer.class);
        BiConsumer<Boolean, Boolean> checkListener = mock(BiConsumer.class);

        Parameter<Integer> edit = settings.addEditBox("edit")
                .type(Integer.class)
                .def(12)
                .onChange(editListener);

        assertEquals(12, edit.getValue().intValue());

        Parameter<String> select = settings.addSelect("select",
                Arrays.asList("option1", "option2", "option3"))
                .type(String.class)
                .def("option2")
                .onChange(selectListener);

        assertEquals("option2", select.getValue());

        Parameter<Boolean> check = settings.addCheckBox("check")
                .def(true)
                .onChange(checkListener);

        assertEquals(true, check.getValue().booleanValue());

        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());

        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        edit.update(1);

        assertEquals(true, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(true, settings.changed());

        verify(editListener).accept(12, 1);
        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        settings.changesReacted();
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());

        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        select.update("option1");
        assertEquals(false, edit.changed());
        assertEquals(true, select.changed());
        assertEquals(false, check.changed());
        assertEquals(true, settings.changed());

        verify(selectListener).accept(1, 0);
        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        settings.changesReacted();
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());

        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        check.update(false);
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(true, check.changed());
        assertEquals(true, settings.changed());

        verify(checkListener).accept(true, false);
        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        settings.changesReacted();
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());

        verifyNoMoreInteractions(editListener, selectListener, checkListener);
    }

    @Test
    public void shouldCallOnChangeListener_whenChangeSomething_withDefaultAndUpdatedValue() {
        // given
        BiConsumer<Integer, Integer> editListener = mock(BiConsumer.class);
        BiConsumer<Integer, Integer> selectListener = mock(BiConsumer.class);
        BiConsumer<Boolean, Boolean> checkListener = mock(BiConsumer.class);

        Parameter<Integer> edit = settings.addEditBox("edit")
                .type(Integer.class)
                .def(12)
                .update(24)
                .onChange(editListener);

        assertEquals(24, edit.getValue().intValue());

        Parameter<String> select = settings.addSelect("select",
                Arrays.asList("option1", "option2", "option3"))
                .type(String.class)
                .def("option2")
                .update("option3")
                .onChange(selectListener);

        assertEquals("option3", select.getValue());

        Parameter<Boolean> check = settings.addCheckBox("check")
                .def(true)
                .update(false)
                .onChange(checkListener);

        assertEquals(false, check.getValue().booleanValue());

        assertEquals(true, edit.changed());
        assertEquals(true, select.changed());
        assertEquals(true, check.changed());
        assertEquals(true, settings.changed());

        settings.changesReacted();
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());

        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        edit.update(1);

        assertEquals(true, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(true, settings.changed());

        verify(editListener).accept(24, 1);
        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        settings.changesReacted();
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());

        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        select.update("option1");
        assertEquals(false, edit.changed());
        assertEquals(true, select.changed());
        assertEquals(false, check.changed());
        assertEquals(true, settings.changed());

        verify(selectListener).accept(2, 0);
        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        settings.changesReacted();
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());

        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        check.update(true);
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(true, check.changed());
        assertEquals(true, settings.changed());

        verify(checkListener).accept(false, true);
        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        settings.changesReacted();
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());

        verifyNoMoreInteractions(editListener, selectListener, checkListener);
    }

    @Test
    public void shouldNotCallOnChangeListener_whenChangeSomething_byJustSetMethod() {
        // given
        BiConsumer<Integer, Integer> editListener = mock(BiConsumer.class);
        BiConsumer<Integer, Integer> selectListener = mock(BiConsumer.class);
        BiConsumer<Boolean, Boolean> checkListener = mock(BiConsumer.class);

        Parameter<Integer> edit = settings.addEditBox("edit")
                .type(Integer.class)
                .onChange(editListener);

        Parameter<String> select = settings.addSelect("select",
                Arrays.asList("option1", "option2", "option3"))
                .onChange(selectListener)
                .type(String.class);

        Parameter<Boolean> check = settings.addCheckBox("check")
                .onChange(checkListener);

        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());

        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        ((EditBox)edit).justSet(1);

        assertEquals(true, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(true, settings.changed());

        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        settings.changesReacted();
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());

        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        ((SelectBox)select).justSet("option1");
        assertEquals(false, edit.changed());
        assertEquals(true, select.changed());
        assertEquals(false, check.changed());
        assertEquals(true, settings.changed());

        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        settings.changesReacted();
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());

        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        ((CheckBox)check).justSet(true);
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(true, check.changed());
        assertEquals(true, settings.changed());

        verifyNoMoreInteractions(editListener, selectListener, checkListener);

        // when then
        settings.changesReacted();
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());

        verifyNoMoreInteractions(editListener, selectListener, checkListener);
    }

    @Test
    public void shouldRemoveParameterByName() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class);
        Parameter<String> select = settings.addSelect("select", Arrays.asList("option1", "option2", "option3")).type(String.class);
        Parameter<Boolean> check = settings.addCheckBox("check");

        // when
        settings.removeParameter("edit");

        // then
        assertEquals(false, settings.hasParameter("edit"));
        assertEquals(true, settings.hasParameter("select"));
        assertEquals(true, settings.hasParameter("check"));
    }

    @Test
    public void shouldClearAllParameters() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class);
        Parameter<String> select = settings.addSelect("select", Arrays.asList("option1", "option2", "option3")).type(String.class);
        Parameter<Boolean> check = settings.addCheckBox("check");

        // when
        settings.clear();

        // then
        assertEquals(false, settings.hasParameter("edit"));
        assertEquals(false, settings.hasParameter("select"));
        assertEquals(false, settings.hasParameter("check"));
    }

    @Test
    public void shouldToStringWorksFine() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").multiline().type(Integer.class).def(10).update(15);
        Parameter<String> select = settings.addSelect("select", Arrays.asList("option1", "option2", "option3")).type(String.class).def("option1").update("option2");
        Parameter<Boolean> check = settings.addCheckBox("check").def(true).update(false);

        // when then
        assertEquals("[edit:Integer = multiline[true] def[10] val[15]]", edit.toString());
        assertEquals("[select:String = options[option1, option2, option3] def[0] val[1]]", select.toString());
        assertEquals("[check:Boolean = def[true] val[false]]", check.toString());
    }

    @Test
    public void shouldSetFlagChangedOnly_whenChangeValue() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class);
        Parameter<String> select = settings.addSelect("select", Arrays.asList("option1", "option2", "option3")).type(String.class);
        Parameter<Boolean> check = settings.addCheckBox("check");

        // when
        edit.update(1);
        select.update("option1");
        check.update(true);
        settings.changesReacted();

        // then
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());

        // when
        edit.update(1);
        select.update("option1");
        check.update(true);

        // then
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());
    }

    @Test
    public void shouldSetFlagChangedOnly_whenChangeValue_nullCases() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class);
        Parameter<String> select = settings.addSelect("select", Arrays.asList("option1", "option2", "option3")).type(String.class);
        Parameter<Boolean> check = settings.addCheckBox("check");

        // when
        edit.update(null);
        check.update(null);
        settings.changesReacted();

        // then
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());

        // when
        edit.update(null);
        check.update(null);

        // then
        assertEquals(false, edit.changed());
        assertEquals(false, select.changed());
        assertEquals(false, check.changed());
        assertEquals(false, settings.changed());
    }

    @Test
    public void shouldSetStringToBoolean_forEditBox() {
        // given
        Parameter<Boolean> edit = settings.addEditBox("edit")
                .type(Boolean.class).def(true);

        assertEquals(true, edit.getValue());

        // when
        List<Parameter> parameters = settings.getParameters();
        parameters.get(0).update("false");

        // then
        assertEquals(false, edit.getValue());
    }

    @Test
    public void shouldToBoolean_forCheckBox() {
        // given
        Parameter<Boolean> edit = settings.addCheckBox("check")
                .type(Boolean.class).def(true);

        assertEquals(true, edit.getValue());

        List<Parameter> parameters = settings.getParameters();
        Parameter parameter = parameters.get(0);

        // when then
        parameter.update("false");
        assertEquals(false, edit.getValue());

        // when then
        parameter.update(true);
        assertEquals(true, edit.getValue());

        // when then
        parameter.update(0);
        assertEquals(false, edit.getValue());

        // when then
        parameter.update("1");
        assertEquals(true, edit.getValue());
    }

    @Test
    public void shouldSetStringToInteger_forEditBox() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit")
                .type(Integer.class).def(21);

        assertEquals(21, edit.getValue().intValue());

        List<Parameter> parameters = settings.getParameters();

        // when then
        parameters.get(0).update("42");
        assertEquals(42, edit.getValue().intValue());
    }

    @Test
    public void shouldToInteger_forCheckBox() {
        // given
        Parameter<Integer> edit = settings.addCheckBox("check")
                .type(Integer.class).def(1);

        assertEquals(1, edit.getValue().intValue());

        List<Parameter> parameters = settings.getParameters();
        Parameter parameter = parameters.get(0);

        // when then
        parameter.update("0");
        assertEquals(0, edit.getValue().intValue());

        // when then
        parameter.update(1);
        assertEquals(1, edit.getValue().intValue());

        // when then
        parameter.update(false);
        assertEquals(0, edit.getValue().intValue());

        // when then
        parameter.update("true");
        assertEquals(1, edit.getValue().intValue());
    }

    @Test
    public void shouldSetStringToDouble_forEditBox() {
        // given
        Parameter<Double> edit = settings.addEditBox("edit")
                .type(Double.class).def(2.1);

        assertEquals(2.1, edit.getValue(), 0);

        List<Parameter> parameters = settings.getParameters();

        // when then
        parameters.get(0).update("4.2");
        assertEquals(4.2, edit.getValue(), 0);
    }

    @Test
    public void shouldSetStringToString_forEditBox() {
        // given
        Parameter<String> edit = settings.addEditBox("edit")
                .type(String.class).def("default");

        assertEquals("default", edit.getValue());

        List<Parameter> parameters = settings.getParameters();

        // when then
        parameters.get(0).update("updated");
        assertEquals("updated", edit.getValue());
    }

    @Test
    public void shouldToString_forCheckBox() {
        // given
        Parameter<String> edit = settings.addCheckBox("check")
                .type(String.class).def("false");

        assertEquals("false", edit.getValue());

        List<Parameter> parameters = settings.getParameters();
        Parameter parameter = parameters.get(0);

        // when then
        parameter.update("true");
        assertEquals("true", edit.getValue());

        // when then
        parameter.update(0);
        assertEquals("false", edit.getValue());

        // when then
        parameter.update("1");
        assertEquals("true", edit.getValue());

        // when then
        parameter.update(false);
        assertEquals("false", edit.getValue());
    }

    public static class Foo {
        int a;
        int b;

        public Foo(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public String toString() {
            return String.format("[%s,%s]", a, b);
        }
    }

    @Test
    public void shouldSetStringToObject_forEditBox() {
        // given
        Parameter<Foo> edit = settings.addEditBox("edit")
                .type(Foo.class).def(new Foo(1, 2))
                .parser(string -> new Foo(
                        Integer.valueOf("" + string.charAt(1)),
                        Integer.valueOf("" + string.charAt(3))));

        assertEquals(1, edit.getValue().a);
        assertEquals(2, edit.getValue().b);

        List<Parameter> parameters = settings.getParameters();

        // when then
        parameters.get(0).update("[3,4]");
        assertEquals(3, edit.getValue().a);
        assertEquals(4, edit.getValue().b);
    }

    @Test
    public void shouldSetStringToObject_forCheckBox() {
        // given
        Parameter<Foo> edit = settings.addCheckBox("check")
                .type(Foo.class).def(new Foo(2, 2))
                .parser(string -> new Foo(
                        (Boolean.valueOf(string)) ? 1 : 0,
                        (Boolean.valueOf(string)) ? 0 : 1));

        assertEquals(2, edit.getValue().a);
        assertEquals(2, edit.getValue().b);

        List<Parameter> parameters = settings.getParameters();

        // when then
        parameters.get(0).update(true);
        assertEquals(1, edit.getValue().a);
        assertEquals(0, edit.getValue().b);

        // when then
        parameters.get(0).update(false);
        assertEquals(0, edit.getValue().a);
        assertEquals(1, edit.getValue().b);
    }

    @Test
    public void shouldWhatChanged() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class);
        Parameter<String> select = settings.addSelect("select", Arrays.asList("option1", "option2", "option3")).type(String.class);
        Parameter<Boolean> check = settings.addCheckBox("check");

        // when then
        edit.update(1);
        assertEquals("[edit]", settings.whatChanged().toString());

        // when then
        select.update("option1");
        assertEquals("[edit, select]", settings.whatChanged().toString());

        // when then
        settings.changesReacted();
        check.update(true);
        assertEquals("[check]", settings.whatChanged().toString());

        // when then
        settings.changesReacted();
        assertEquals("[]", settings.whatChanged().toString());

        // when then
        // same value
        edit.update(1);
        select.update("option1");
        check.update(true);
        assertEquals("[]", settings.whatChanged().toString());
    }

    @Test
    public void shouldUpdateAll() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class).def(12);
        Parameter<String> select = settings.addSelect("select", Arrays.asList("option1", "option2", "option3")).type(String.class).def("option1");
        Parameter<Boolean> check = settings.addCheckBox("check").def(true);

        assertEquals("[[edit:Integer = multiline[false] def[12] val[null]], " +
                "[select:String = options[option1, option2, option3] def[0] val[null]], " +
                "[check:Boolean = def[true] val[null]]]",
                settings.getParameters().toString());

        // when
        settings.updateAll(new LinkedList<Parameter>(){{
            add(new EditBox<String>("edit").type(String.class).multiline(true).def("123").update("24"));
            add(new CheckBox("check").type(String.class).def(false).update("0"));
            add(new SelectBox("new", Arrays.asList(1, 2, 3)).type(Integer.class).def(1).update(3));
        }});

        // then
        assertEquals(
                // для существующих обновится только value
                "[[edit:Integer = multiline[false] def[12] val[24]], " +
                "[select:String = options[option1, option2, option3] def[0] val[null]], " +
                "[check:Boolean = def[true] val[false]], " +
                // новые запишутся полностью
                "[new:String = options[1, 2, 3] def[0] val[2]]]",
                settings.getParameters().toString());
    }

    @Test
    public void shouldClone_caseLeaveSameName() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class).def(12);
        Parameter<String> select = settings.addSelect("select", Arrays.asList("option1", "option2", "option3")).type(String.class).def("option1");
        Parameter<Boolean> check = settings.addCheckBox("check").def(true);
        Parameter<String> simple = new SimpleParameter<>("key", "value");

        assertEquals("[[edit:Integer = multiline[false] def[12] val[null]], " +
                        "[select:String = options[option1, option2, option3] def[0] val[null]], " +
                        "[check:Boolean = def[true] val[null]]]",
                settings.getParameters().toString());

        assertEquals("[key:String = val[value]]",
                simple.toString());

        // when
        Parameter<Integer> editClone = edit.clone(null);
        Parameter<String> selectClone = select.clone(null);
        Parameter<Boolean> checkClone = check.clone(null);
        Parameter<String> simpleClone = simple.clone(null);

        // then
        // проверяем что в клоны скопировались значения
        assertEquals("[[edit:Integer = multiline[false] def[12] val[null]], " +
                        "[select:String = options[option1, option2, option3] def[0] val[null]], " +
                        "[check:Boolean = def[true] val[null]]]",
                Arrays.asList(editClone, selectClone, checkClone).toString());

        assertEquals("[key:String = val[value]]",
                simpleClone.toString());

        // when
        // меняем значения
        editClone.update(23);
        selectClone.update("option2");
        checkClone.update(false);
        simpleClone.update("other");

        // then
        // исходные параметры не меняются
        assertEquals("[[edit:Integer = multiline[false] def[12] val[null]], " +
                        "[select:String = options[option1, option2, option3] def[0] val[null]], " +
                        "[check:Boolean = def[true] val[null]]]",
                settings.getParameters().toString());

        assertEquals("[key:String = val[value]]",
                simple.toString());

        // а клонированные меняются
        assertEquals("[[edit:Integer = multiline[false] def[12] val[23]], " +
                        "[select:String = options[option1, option2, option3] def[0] val[1]], " +
                        "[check:Boolean = def[true] val[false]]]",
                Arrays.asList(editClone, selectClone, checkClone).toString());
        // TODO to use Utils.split here

        assertEquals("[key:String = val[other]]",
                simpleClone.toString());
    }

    @Test
    public void shouldClone_caseChangeName() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class).def(12);
        Parameter<String> select = settings.addSelect("select", Arrays.asList("option1", "option2", "option3")).type(String.class).def("option1");
        Parameter<Boolean> check = settings.addCheckBox("check").def(true);
        Parameter<String> simple = new SimpleParameter<>("key", "value");

        assertEquals("[[edit:Integer = multiline[false] def[12] val[null]], " +
                        "[select:String = options[option1, option2, option3] def[0] val[null]], " +
                        "[check:Boolean = def[true] val[null]]]",
                settings.getParameters().toString());

        assertEquals("[key:String = val[value]]",
                simple.toString());

        // when
        Parameter<Integer> editClone = edit.clone("newEdit");
        Parameter<String> selectClone = select.clone("newSelect");
        Parameter<Boolean> checkClone = check.clone("newCheck");
        Parameter<String> simpleClone = simple.clone("newKey");

        // then
        // проверяем что в клоны скопировались значения
        assertEquals("[[newEdit:Integer = multiline[false] def[12] val[null]], " +
                        "[newSelect:String = options[option1, option2, option3] def[0] val[null]], " +
                        "[newCheck:Boolean = def[true] val[null]]]",
                Arrays.asList(editClone, selectClone, checkClone).toString());

        assertEquals("[newKey:String = val[value]]",
                simpleClone.toString());

        // when
        // меняем значения
        editClone.update(23);
        selectClone.update("option2");
        checkClone.update(false);
        simpleClone.update("other");

        // then
        // исходные параметры не меняются
        assertEquals("[[edit:Integer = multiline[false] def[12] val[null]], " +
                        "[select:String = options[option1, option2, option3] def[0] val[null]], " +
                        "[check:Boolean = def[true] val[null]]]",
                settings.getParameters().toString());

        assertEquals("[key:String = val[value]]",
                simple.toString());

        // а клонированные меняются
        assertEquals("[[newEdit:Integer = multiline[false] def[12] val[23]], " +
                        "[newSelect:String = options[option1, option2, option3] def[0] val[1]], " +
                        "[newCheck:Boolean = def[true] val[false]]]",
                Arrays.asList(editClone, selectClone, checkClone).toString());
        // TODO to use Utils.split here

        assertEquals("[newKey:String = val[other]]",
                simpleClone.toString());
    }

    @Test
    public void shouldSelectBox_index() {
        // given
        SelectBox<String> select = settings.addSelect("select",
                Arrays.asList("option1", "option2")).type(String.class);

        // when then
        select.select(1);
        assertEquals("option2", select.getValue());
        assertEquals(1, select.index());

        // when then
        select.select(0);
        assertEquals("option1", select.getValue());
        assertEquals(0, select.index());
    }

    @Test
    public void shouldSelectBox_indexOfBound() {
        // given
        SelectBox<String> select = settings.addSelect("select",
                Arrays.asList("option1", "option2")).type(String.class);

        // when then
        select.select(3);
        assertEquals(null, select.getValue());
        assertEquals(-1, select.index());
    }

    @Test
    public void shouldReplaceParameter_caseEditBox() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class);
        Parameter<Integer> select = settings.addSelect("select", Arrays.asList("option1")).type(Integer.class);
        Parameter<Integer> check = settings.addCheckBox("check").type(Integer.class);
        
        // when
        settings.replaceParameter(
                new EditBox("edit")
                        .type(String.class)
                        .def("defaultValue")
                        .update("updatedValue"));

        // then
        String expected = "[[edit:String = multiline[false] def[defaultValue] val[updatedValue]], \n" +
                "[select:String = options[option1] def[null] val[null]], \n" +
                "[check:Integer = def[null] val[null]]]";

        assertEquals(expected,
                split(settings.getParameters(), "], \n["));

        // when
        edit.update(2);

        // then
        // original parameter will only change
        assertEquals("[edit:Integer = multiline[false] def[null] val[2]]", edit.toString());
        assertEquals(expected,
                split(settings.getParameters(), "], \n["));
    }

    @Test
    public void shouldReplaceParameter_caseSelect() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class);
        Parameter<Integer> select = settings.addSelect("select", Arrays.asList(1, 2, 3)).type(Integer.class);
        Parameter<Integer> check = settings.addCheckBox("check").type(Integer.class);

        // when
        settings.replaceParameter(
                new SelectBox("select", Arrays.asList("option1", "option2"))
                        .type(String.class)
                        .def("option2")
                        .update("option1"));

        // then
        String expected = "[[edit:Integer = multiline[false] def[null] val[null]], \n" +
                "[select:String = options[option1, option2] def[1] val[0]], \n" +
                "[check:Integer = def[null] val[null]]]";

        assertEquals(expected,
                split(settings.getParameters(), "], \n["));

        // when
        select.update(2);

        // then
        // original parameter will only change
        assertEquals("[select:String = options[1, 2, 3] def[null] val[1]]", select.toString());
        assertEquals(expected,
                split(settings.getParameters(), "], \n["));
    }

    @Test
    public void shouldReplaceParameter_caseCheckBox() {
        // given
        Parameter<Integer> edit = settings.addEditBox("edit").type(Integer.class);
        Parameter<Integer> select = settings.addSelect("select", Arrays.asList(1, 2, 3)).type(Integer.class);
        Parameter<Integer> check = settings.addCheckBox("check").type(Integer.class);

        // when
        settings.replaceParameter(
                new CheckBox("check")
                        .type(Boolean.class)
                        .def(true)
                        .update(false));

        // then
        String expected = "[[edit:Integer = multiline[false] def[null] val[null]], \n" +
                "[select:String = options[1, 2, 3] def[null] val[null]], \n" +
                "[check:Boolean = def[true] val[false]]]";

        assertEquals(expected, split(settings.getParameters(), "], \n["));

        // when
        check.update(true);

        // then
        // original parameter will only change
        assertEquals("[check:Integer = def[null] val[1]]", check.toString());
        assertEquals(expected, split(settings.getParameters(), "], \n["));
    }

    @Test
    public void shouldCopyFrom() {
        // given
        Parameter<Integer> edit1 = settings.addEditBox("edit").type(Integer.class);
        Parameter<Integer> select1 = settings.addSelect("select", Arrays.asList(1, 2, 3)).type(Integer.class);
        Parameter<Integer> check1 = settings.addCheckBox("check").type(Integer.class);
        Parameter<String> edit2 = settings.addEditBox("edit2").type(String.class);
        Parameter<String> select2 = settings.addSelect("select2", Arrays.asList("option1", "option2")).type(String.class);
        Parameter<Boolean> check2 = settings.addCheckBox("check2").type(Boolean.class);

        // when
        settings.copyFrom(Arrays.asList(
                new CheckBox("check")
                        .type(Boolean.class)
                        .def(true)
                        .update(false),
                new SelectBox("select", Arrays.asList("option1", "option2"))
                        .type(String.class)
                        .def("option2")
                        .update("option1"),
                new EditBox("edit")
                        .type(String.class)
                        .def("defaultValue")
                        .update("updatedValue")
        ));

        // then
        assertEquals("[[edit:String = multiline[false] def[defaultValue] val[updatedValue]], \n" +
                "[select:String = options[option1, option2] def[1] val[0]], \n" +
                "[check:Boolean = def[true] val[false]], \n" +
                "[edit2:String = multiline[false] def[null] val[null]], \n" +
                "[select2:String = options[option1, option2] def[null] val[null]], \n" +
                "[check2:Boolean = def[null] val[null]]]",
                split(settings.getParameters(), "], \n["));

        // when
        edit2.update("text");
        select2.update("option2");
        check2.update(false);

        // then
        // original parameter will only change
        assertEquals("[edit2:String = multiline[false] def[null] val[text]]", edit2.toString());
        assertEquals("[select2:String = options[option1, option2] def[null] val[1]]", select2.toString());
        assertEquals("[check2:Boolean = def[null] val[false]]", check2.toString());

        assertEquals("[[edit:String = multiline[false] def[defaultValue] val[updatedValue]], \n" +
                "[select:String = options[option1, option2] def[1] val[0]], \n" +
                "[check:Boolean = def[true] val[false]], \n" +
                "[edit2:String = multiline[false] def[null] val[text]], \n" +
                "[select2:String = options[option1, option2] def[null] val[1]], \n" +
                "[check2:Boolean = def[null] val[false]]]",
                split(settings.getParameters(), "], \n["));
    }

    @Test
    public void shouldReplaceAll() {
        // given
        Parameter<Integer> edit1 = settings.addEditBox("edit").type(Integer.class);
        Parameter<Integer> select1 = settings.addSelect("select", Arrays.asList(1, 2, 3)).type(Integer.class);
        Parameter<Integer> check1 = settings.addCheckBox("check").type(Integer.class);
        Parameter<String> edit2 = settings.addEditBox("edit2").type(String.class);
        Parameter<String> select2 = settings.addSelect("select2", Arrays.asList("option1", "option2")).type(String.class);
        Parameter<Boolean> check2 = settings.addCheckBox("check2").type(Boolean.class);

        // when
        settings.replaceAll(Arrays.asList("edit", "check"),
                Arrays.asList(
                        // был удален потому что указан выше
                        new CheckBox("check")
                                .type(Boolean.class)
                                .def(true)
                                .update(false),

                        // не был удален, но все равно перезапишется
                        new SelectBox("select", Arrays.asList("option1", "option2"))
                                .type(String.class)
                                .def("option2")
                                .update("option1"),

                        // оригинал был удален, а этот просто допишется
                        new EditBox("editWithKeyUpdated")
                                .type(String.class)
                                .def("defaultValue")
                                .update("updatedValue")));

        // then
        assertEquals("[[select:String = options[option1, option2] def[1] val[0]], \n" +
                        "[edit2:String = multiline[false] def[null] val[null]], \n" +
                        "[select2:String = options[option1, option2] def[null] val[null]], \n" +
                        "[check2:Boolean = def[null] val[null]], \n" +
                        "[check:Boolean = def[true] val[false]], \n" +
                        "[editWithKeyUpdated:String = multiline[false] def[defaultValue] val[updatedValue]]]",
                split(settings.getParameters(), "], \n["));

        // when
        edit1.update(12);
        select1.update(3);
        check1.update(false);

        edit2.update("text");
        select2.update("option2");
        check2.update(false);

        // then
        // original parameter will only change
        assertEquals("[edit:Integer = multiline[false] def[null] val[12]]", edit1.toString());
        assertEquals("[select:String = options[1, 2, 3] def[null] val[2]]", select1.toString());
        assertEquals("[check:Integer = def[null] val[0]]", check1.toString());

        assertEquals("[edit2:String = multiline[false] def[null] val[text]]", edit2.toString());
        assertEquals("[select2:String = options[option1, option2] def[null] val[1]]", select2.toString());
        assertEquals("[check2:Boolean = def[null] val[false]]", check2.toString());

        assertEquals("[[select:String = options[option1, option2] def[1] val[0]], \n" +
                        "[edit2:String = multiline[false] def[null] val[text]], \n" +
                        "[select2:String = options[option1, option2] def[null] val[1]], \n" +
                        "[check2:Boolean = def[null] val[false]], \n" +
                        "[check:Boolean = def[true] val[false]], \n" +
                        "[editWithKeyUpdated:String = multiline[false] def[defaultValue] val[updatedValue]]]",
                split(settings.getParameters(), "], \n["));
    }

    @Test
    public void shouldUpdateAllFiltered() {
        // given
        Parameter<String> edit1 = settings.addEditBox("otherEdit1").type(String.class).def("default1").update("value1");
        Parameter<String> edit2 = settings.addEditBox("edit2").type(String.class).def("default2").update("value2");
        Parameter<String> edit3 = settings.addEditBox("edit3").type(String.class).def("default3").update("value3");
        Parameter<String> edit4 = settings.addEditBox("edit4").type(String.class).def("default4").update("value4");
        Parameter<String> edit5 = settings.addEditBox("edit5").type(String.class).def("default5").update("value5");
        Parameter<String> edit6 = settings.addEditBox("edit6").type(String.class).def("default6").update("value6");
        Parameter<String> edit7 = settings.addEditBox("edit7").type(String.class).def("default7").update("value7");
        Parameter<String> edit8 = settings.addEditBox("edit8").type(String.class).def("default8").update("value8");
        Parameter<String> edit9 = settings.addEditBox("edit9").type(String.class).def("default9").update("value9");
        Parameter<String> edit10 = settings.addEditBox("otherEdit10").type(String.class).def("default10").update("value10");

        assertEquals("[[otherEdit1:String = multiline[false] def[default1] val[value1]], \n" +
                        "[edit2:String = multiline[false] def[default2] val[value2]], \n" +
                        "[edit3:String = multiline[false] def[default3] val[value3]], \n" +
                        "[edit4:String = multiline[false] def[default4] val[value4]], \n" +
                        "[edit5:String = multiline[false] def[default5] val[value5]], \n" +
                        "[edit6:String = multiline[false] def[default6] val[value6]], \n" +
                        "[edit7:String = multiline[false] def[default7] val[value7]], \n" +
                        "[edit8:String = multiline[false] def[default8] val[value8]], \n" +
                        "[edit9:String = multiline[false] def[default9] val[value9]], \n" +
                        "[otherEdit10:String = multiline[false] def[default10] val[value10]]]",
                split(settings.getParameters(), "], \n["));

        // when
        Predicate<Parameter> filter = parameter -> parameter.getName().startsWith("edit");
        settings.updateAll(filter,
                Arrays.asList("edit2",     "edit3"   ,  "edit4",  "edit5",  "edit6",    "edit7", "edit8",    "edit9"),
                Arrays.asList("edit2",     "newEdit3",  "edit4",  "",       "newEdit6", "edit7", "edit9",    "edit8"),
                Arrays.asList("newValue2", "newValue3", "value4", "value5", "value6",   "",      "newEdit9", "edit9"));

        // then
        assertEquals("[[otherEdit1:String = multiline[false] def[default1] val[value1]], \n" +
                        "[otherEdit10:String = multiline[false] def[default10] val[value10]], \n" +
                        "[edit2:String = multiline[false] def[default2] val[newValue2]], \n" +
                        "[edit4:String = multiline[false] def[default4] val[value4]], \n" +
                        "[edit7:String = multiline[false] def[default7] val[]], \n" +
                        "[edit8:String = multiline[false] def[default9] val[edit9]], \n" +
                        "[edit9:String = multiline[false] def[default8] val[newEdit9]], \n" +
                        "[newEdit3:String = multiline[false] def[default3] val[newValue3]], \n" +
                        "[newEdit6:String = multiline[false] def[default6] val[value6]]]",
                split(settings.getParameters(), "], \n["));
    }
}
