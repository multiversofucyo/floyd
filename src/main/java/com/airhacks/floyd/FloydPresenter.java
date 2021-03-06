package com.airhacks.floyd;

/*
 * #%L
 * igniter
 * %%
 * Copyright (C) 2013 Adam Bien
 * %%
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
 * #L%
 */
import com.airhacks.floyd.cloud.CloudView;
import com.airhacks.floyd.scanner.ScannerView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author adam-bien.com
 */
public class FloydPresenter implements Initializable {

    @FXML
    AnchorPane scan;

    @FXML
    AnchorPane active;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ScannerView scanner = new ScannerView();
        scanner.getViewAsync(scan.getChildren()::add);
        CloudView cloudy = new CloudView();
        cloudy.getViewAsync(active.getChildren()::add);
    }
}
