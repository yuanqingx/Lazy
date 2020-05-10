package com.qingci.lazy;

import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiPackage;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LazyLayout {
    private JPanel converter;
    private JTextField target;
    private JTextField source;
    private JTextField pkg;
    private JButton choosePackageButton;
    private JButton chooseSourceButton;
    private JButton chooseTargetButton;

    private ConverterData converterData;

    private Project project;

    public LazyLayout(Project project) {
        this.project = project;
        converterData = new ConverterData();
        setupUI();
    }


    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void setupUI() {
        converter = new JPanel();
        converter.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));

        final JLabel label1 = new JLabel();
        label1.setText("Source");
        converter.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(31, 30), null, 0, false));

        final JLabel PackageLabel = new JLabel();
        PackageLabel.setText("Package");
        converter.add(PackageLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        buildPackageField();

        final JLabel targetLabel = new JLabel();
        targetLabel.setText("Target");
        converter.add(targetLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(31, 30), null, 0, false));

        source = new JTextField();
        converter.add(source, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));

        target = new JTextField();
        converter.add(target, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));

        buildChoosePackageButton();

        buildChooseSourceButton();

        buildChooseTargetButton();
    }

    private void buildPackageField() {
        pkg = new JTextField();
        String choosePkg = PropertiesComponent.getInstance(project).getValue(project.getLocationHash() + "_choosePkg");
        pkg.setText(choosePkg);
        converter.add(pkg, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    private void buildChooseTargetButton() {
        chooseTargetButton = new JButton();
        chooseTargetButton.setText("Choose Target");
        chooseTargetButton.addActionListener(e -> {
            PsiClass selected = chooseDialog("Choose Target");
            if (Objects.nonNull(selected)) {
                target.setText(selected.getName());
                converterData.setTargetClass(selected);
            }
        });
        converter.add(chooseTargetButton, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    private void buildChooseSourceButton() {
        chooseSourceButton = new JButton();
        chooseSourceButton.setText("Choose Source");
        chooseSourceButton.addActionListener(e -> {
            PsiClass selected = chooseDialog("Choose Source");
            if (Objects.nonNull(selected)) {
                source.setText(selected.getName());
                converterData.setSourceClass(selected);
            }
        });
        converter.add(chooseSourceButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    private PsiClass chooseDialog(String title) {
        TreeClassChooserFactory instance = TreeClassChooserFactory.getInstance(project);
        TreeClassChooser chooser = instance.createProjectScopeChooser(title);
        chooser.showDialog();
        return chooser.getSelected();
    }

    private void buildChoosePackageButton() {
        choosePackageButton = new JButton();
        choosePackageButton.setText("Choose Package");
        choosePackageButton.addActionListener(e -> {
            System.out.println("choose package");
            PackageChooserDialog packageChooserDialog = new PackageChooserDialog("Choose Package", project);
            packageChooserDialog.show();
            PsiPackage selectedPackage = packageChooserDialog.getSelectedPackage();
            if (Objects.nonNull(selectedPackage)) {
                String pkgPath = selectedPackage.getQualifiedName();
                pkg.setText(pkgPath);
                Cache.setConvertPackage(selectedPackage);

                PropertiesComponent.getInstance(project).setValue(project.getLocationHash() + "_choosePkg",selectedPackage.getQualifiedName());

                converterData.setConverterPackage(selectedPackage);
            }
        });
        converter.add(choosePackageButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return converter;
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("demo");
        frame.setContentPane(new LazyLayout(null).converter);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public ConverterData getConvertData() {
        return converterData;
    }
}
