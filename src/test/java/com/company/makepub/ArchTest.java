package com.company.makepub;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArchTest {
    private final JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(new ImportOption.DoNotIncludeTests())
            .importPackages("com.company.makepub..");

    @Test
    void domainShouldNotDependeOnOutside(){
        noClasses()
                .that()
                .resideInAPackage("com.company.makepub.app.domain")
                .should()
                .dependOnClassesThat()
                .resideOutsideOfPackages(
                        "com.company.makepub.app.domain..",
                        "jakarta..",
                        "java..")
                .check(importedClasses);
    }

    @Test
    void applicationShouldNotDependeOnOutside(){
        noClasses()
                .that()
                .resideInAnyPackage("com.company.makepub.app.gateways",
                        "com.company.makepub.app.usecases")
                .should()
                .dependOnClassesThat()
                .resideOutsideOfPackages(
                        "com.company.makepub.app..",
                        "jakarta..",
                        "java..")
                .allowEmptyShould(true)
                .check(importedClasses);
    }
}
