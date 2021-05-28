package org.openapitools.codegen.java.jaxrs;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.parser.core.models.ParseOptions;

import org.openapitools.codegen.*;
import org.openapitools.codegen.config.CodegenConfigurator;
import org.openapitools.codegen.languages.AbstractJavaJAXRSServerCodegen;
import org.openapitools.codegen.languages.JavaClientCodegen;
import org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen;
import org.openapitools.codegen.languages.features.CXFServerFeatures;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.openapitools.codegen.TestUtils.assertFileContains;
import static org.openapitools.codegen.TestUtils.validateJavaSourceFiles;
import static org.testng.Assert.assertTrue;

/**
 * Unit-Test for {@link org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen}.
 *
 * @author attrobit
 */
public class JavaJAXRSSpecServerCodegenTest extends JavaJaxrsBaseTest {

    @BeforeMethod
    public void before() {
        codegen = new JavaJAXRSSpecServerCodegen();
    }

    @Test
    public void testInitialConfigValues() throws Exception {
        final JavaJAXRSSpecServerCodegen codegen = new JavaJAXRSSpecServerCodegen();
        codegen.processOpts();

        OpenAPI openAPI = new OpenAPI();
        openAPI.addServersItem(new Server().url("https://api.abcde.xy:8082/v2"));
        codegen.preprocessOpenAPI(openAPI);

        Assert.assertEquals(codegen.additionalProperties().get(CodegenConstants.HIDE_GENERATION_TIMESTAMP), Boolean.FALSE);
        Assert.assertEquals(codegen.isHideGenerationTimestamp(), false);
        Assert.assertEquals(codegen.modelPackage(), "org.openapitools.model");
        Assert.assertEquals(codegen.additionalProperties().get(CodegenConstants.MODEL_PACKAGE), "org.openapitools.model");
        Assert.assertEquals(codegen.apiPackage(), "org.openapitools.api");
        Assert.assertEquals(codegen.additionalProperties().get(CodegenConstants.API_PACKAGE), "org.openapitools.api");
        Assert.assertEquals(codegen.getInvokerPackage(), "org.openapitools.api");
        Assert.assertEquals(codegen.additionalProperties().get(CodegenConstants.INVOKER_PACKAGE), "org.openapitools.api");
        Assert.assertEquals(codegen.additionalProperties().get(JavaJAXRSSpecServerCodegen.SERVER_PORT), "8082");
        Assert.assertEquals(codegen.getOpenApiSpecFileLocation(), "src/main/openapi/openapi.yaml");
        Assert.assertEquals(codegen.additionalProperties().get(JavaJAXRSSpecServerCodegen.OPEN_API_SPEC_FILE_LOCATION), "src/main/openapi/openapi.yaml");
    }

    @Test
    public void testSettersForConfigValues() throws Exception {
        final JavaJAXRSSpecServerCodegen codegen = new JavaJAXRSSpecServerCodegen();
        codegen.setHideGenerationTimestamp(true);
        codegen.setModelPackage("xx.yyyyyyyy.model");
        codegen.setApiPackage("xx.yyyyyyyy.api");
        codegen.setInvokerPackage("xx.yyyyyyyy.invoker");
        codegen.setOpenApiSpecFileLocation("src/main/resources/META-INF/openapi.yaml");
        codegen.processOpts();

        Assert.assertEquals(codegen.additionalProperties().get(CodegenConstants.HIDE_GENERATION_TIMESTAMP), Boolean.TRUE);
        Assert.assertEquals(codegen.isHideGenerationTimestamp(), true);
        Assert.assertEquals(codegen.modelPackage(), "xx.yyyyyyyy.model");
        Assert.assertEquals(codegen.additionalProperties().get(CodegenConstants.MODEL_PACKAGE), "xx.yyyyyyyy.model");
        Assert.assertEquals(codegen.apiPackage(), "xx.yyyyyyyy.api");
        Assert.assertEquals(codegen.additionalProperties().get(CodegenConstants.API_PACKAGE), "xx.yyyyyyyy.api");
        Assert.assertEquals(codegen.getInvokerPackage(), "xx.yyyyyyyy.invoker");
        Assert.assertEquals(codegen.additionalProperties().get(CodegenConstants.INVOKER_PACKAGE), "xx.yyyyyyyy.invoker");
        Assert.assertEquals(codegen.getOpenApiSpecFileLocation(), "src/main/resources/META-INF/openapi.yaml");
        Assert.assertEquals(codegen.additionalProperties().get(JavaJAXRSSpecServerCodegen.OPEN_API_SPEC_FILE_LOCATION), "src/main/resources/META-INF/openapi.yaml");
    }

    @Test
    public void testAdditionalPropertiesPutForConfigValues() throws Exception {
        final JavaJAXRSSpecServerCodegen codegen = new JavaJAXRSSpecServerCodegen();
        codegen.additionalProperties().put(CodegenConstants.HIDE_GENERATION_TIMESTAMP, "true");
        codegen.additionalProperties().put(CodegenConstants.MODEL_PACKAGE, "xyz.yyyyy.mmmmm.model");
        codegen.additionalProperties().put(CodegenConstants.API_PACKAGE, "xyz.yyyyy.aaaaa.api");
        codegen.additionalProperties().put(CodegenConstants.INVOKER_PACKAGE, "xyz.yyyyy.iiii.invoker");
        codegen.additionalProperties().put("serverPort", "8088");
        codegen.additionalProperties().put(JavaJAXRSSpecServerCodegen.OPEN_API_SPEC_FILE_LOCATION, "openapi.yml");
        codegen.processOpts();

        OpenAPI openAPI = new OpenAPI();
        openAPI.addServersItem(new Server().url("https://api.abcde.xy:8082/v2"));
        codegen.preprocessOpenAPI(openAPI);

        Assert.assertEquals(codegen.additionalProperties().get(CodegenConstants.HIDE_GENERATION_TIMESTAMP), Boolean.TRUE);
        Assert.assertEquals(codegen.isHideGenerationTimestamp(), true);
        Assert.assertEquals(codegen.modelPackage(), "xyz.yyyyy.mmmmm.model");
        Assert.assertEquals(codegen.additionalProperties().get(CodegenConstants.MODEL_PACKAGE), "xyz.yyyyy.mmmmm.model");
        Assert.assertEquals(codegen.apiPackage(), "xyz.yyyyy.aaaaa.api");
        Assert.assertEquals(codegen.additionalProperties().get(CodegenConstants.API_PACKAGE), "xyz.yyyyy.aaaaa.api");
        Assert.assertEquals(codegen.getInvokerPackage(), "xyz.yyyyy.iiii.invoker");
        Assert.assertEquals(codegen.additionalProperties().get(CodegenConstants.INVOKER_PACKAGE), "xyz.yyyyy.iiii.invoker");
        Assert.assertEquals(codegen.additionalProperties().get(AbstractJavaJAXRSServerCodegen.SERVER_PORT), "8088");
        Assert.assertEquals(codegen.getOpenApiSpecFileLocation(), "openapi.yml");
        Assert.assertEquals(codegen.additionalProperties().get(JavaJAXRSSpecServerCodegen.OPEN_API_SPEC_FILE_LOCATION), "openapi.yml");
    }

    /**
     * Test
     * {@link JavaJAXRSSpecServerCodegen#addOperationToGroup(String, String, Operation, CodegenOperation, Map)} for Resource with path "/" without "useTags"
     */
    @Test
    public void testAddOperationToGroupForRootResourceAndUseTagsFalse() {
        CodegenOperation codegenOperation = new CodegenOperation();
        codegenOperation.operationId = "findPrimaryresource";
        codegenOperation.path = "/";
        Operation operation = new Operation();
        Map<String, List<CodegenOperation>> operationList = new HashMap<>();

        codegen.addOperationToGroup("Primaryresource", "/", operation, codegenOperation, operationList);

        Assert.assertEquals(operationList.size(), 1);
        Assert.assertTrue(operationList.containsKey("default"));
        Assert.assertEquals(codegenOperation.baseName, "default");
    }

    /**
     * Test
     * {@link JavaJAXRSSpecServerCodegen#addOperationToGroup(String, String, Operation, CodegenOperation, Map)} for Resource with path "/" with "useTags"
     */
    @Test
    public void testAddOperationToGroupForRootResourceAndUseTagsTrue() {
        CodegenOperation codegenOperation = new CodegenOperation();
        codegenOperation.operationId = "findPrimaryresource";
        codegenOperation.path = "/";
        Operation operation = new Operation();
        Map<String, List<CodegenOperation>> operationList = new HashMap<>();
        codegen.setUseTags(true);

        codegen.addOperationToGroup("Primaryresource", "/", operation, codegenOperation, operationList);

        Assert.assertEquals(operationList.size(), 1);
        Assert.assertTrue(operationList.containsKey("Primaryresource"));
        Assert.assertEquals(codegenOperation.baseName, "Primaryresource");
    }

    /**
     * Test
     * {@link JavaJAXRSSpecServerCodegen#addOperationToGroup(String, String, Operation, CodegenOperation, Map)} for Resource with path param.
     */
    @Test
    public void testAddOperationToGroupForRootResourcePathParamAndUseTagsFalse() {
        CodegenOperation codegenOperation = new CodegenOperation();
        codegenOperation.operationId = "getPrimaryresource";
        codegenOperation.path = "/{uuid}";
        Operation operation = new Operation();
        Map<String, List<CodegenOperation>> operationList = new HashMap<>();

        codegen.addOperationToGroup("Primaryresource", "/{uuid}", operation, codegenOperation, operationList);

        Assert.assertEquals(operationList.size(), 1);
        Assert.assertTrue(operationList.containsKey("default"));
    }

    /**
     * Test
     * {@link JavaJAXRSSpecServerCodegen#addOperationToGroup(String, String, Operation, CodegenOperation, Map)} for Resource with path param.
     */
    @Test
    public void testAddOperationToGroupForRootResourcePathParamAndUseTagsTrue() {
        CodegenOperation codegenOperation = new CodegenOperation();
        codegenOperation.operationId = "getPrimaryresource";
        codegenOperation.path = "/{uuid}";
        Operation operation = new Operation();
        Map<String, List<CodegenOperation>> operationList = new HashMap<>();
        codegen.setUseTags(true);

        codegen.addOperationToGroup("Primaryresource", "/{uuid}", operation, codegenOperation, operationList);

        Assert.assertEquals(operationList.size(), 1);
        Assert.assertTrue(operationList.containsKey("Primaryresource"));
        Assert.assertEquals(codegenOperation.baseName, "Primaryresource");
    }

    /**
     * Test
     * {@link JavaJAXRSSpecServerCodegen#addOperationToGroup(String, String,
     * Operation, CodegenOperation, Map)} for Resource with path "/subresource".
     */
    @Test
    public void testAddOperationToGroupForSubresource() {
        CodegenOperation codegenOperation = new CodegenOperation();
        codegenOperation.path = "/subresource";
        Operation operation = new Operation();
        Map<String, List<CodegenOperation>> operationList = new HashMap<>();

        codegen.addOperationToGroup("Default", "/subresource", operation, codegenOperation, operationList);

        Assert.assertEquals(codegenOperation.baseName, "subresource");
        Assert.assertEquals(operationList.size(), 1);
        assertTrue(operationList.containsKey("subresource"));
    }

    /**
     * Test {@link JavaJAXRSSpecServerCodegen#toApiName(String)} with subresource.
     */
    @Test
    public void testToApiNameForSubresource() {
        final String subresource = codegen.toApiName("subresource");
        Assert.assertEquals(subresource, "SubresourceApi");
    }

    @Test
    public void testGeneratePingDefaultLocation() throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put(JavaClientCodegen.JAVA8_MODE, true);

        File output = Files.createTempDirectory("test").toFile();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("jaxrs-spec")
                .setAdditionalProperties(properties)
                .setInputSpec("src/test/resources/3_0/ping.yaml")
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        DefaultGenerator generator = new DefaultGenerator();
        List<File> files = generator.opts(clientOptInput).generate();

        validateJavaSourceFiles(files);

        TestUtils.ensureContainsFile(files, output, "src/main/openapi/openapi.yaml");

        output.deleteOnExit();
    }

    @Test
    public void testGeneratePingNoSpecFile() throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put(JavaClientCodegen.JAVA8_MODE, true);
        properties.put(JavaJAXRSSpecServerCodegen.OPEN_API_SPEC_FILE_LOCATION, "");

        File output = Files.createTempDirectory("test").toFile();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("jaxrs-spec")
                .setAdditionalProperties(properties)
                .setInputSpec("src/test/resources/3_0/ping.yaml")
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        DefaultGenerator generator = new DefaultGenerator();
        List<File> files = generator.opts(clientOptInput).generate();

        validateJavaSourceFiles(files);
        TestUtils.ensureDoesNotContainsFile(files, output, "src/main/openapi/openapi.yaml");

        output.deleteOnExit();
    }

    @Test
    public void testGeneratePingAlternativeLocation1() throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put(JavaClientCodegen.JAVA8_MODE, true);
        properties.put(JavaJAXRSSpecServerCodegen.OPEN_API_SPEC_FILE_LOCATION, "src/main/resources/META-INF/openapi.yaml");

        File output = Files.createTempDirectory("test").toFile();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("jaxrs-spec")
                .setAdditionalProperties(properties)
                .setInputSpec("src/test/resources/3_0/ping.yaml")
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        DefaultGenerator generator = new DefaultGenerator();
        List<File> files = generator.opts(clientOptInput).generate();

        validateJavaSourceFiles(files);

        TestUtils.ensureContainsFile(files, output, "src/main/resources/META-INF/openapi.yaml");

        output.deleteOnExit();
    }

    @Test
    public void testGeneratePingAlternativeLocation2() throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put(JavaClientCodegen.JAVA8_MODE, true);
        properties.put(JavaJAXRSSpecServerCodegen.OPEN_API_SPEC_FILE_LOCATION, "openapi.yml");

        File output = Files.createTempDirectory("test").toFile();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("jaxrs-spec")
                .setAdditionalProperties(properties)
                .setInputSpec("src/test/resources/3_0/ping.yaml")
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        DefaultGenerator generator = new DefaultGenerator();
        List<File> files = generator.opts(clientOptInput).generate();

        validateJavaSourceFiles(files);
        TestUtils.ensureContainsFile(files, output, "openapi.yml");

        output.deleteOnExit();
    }

    @Test
    public void testGenerateApiWithPreceedingPathParameter_issue1347() throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put(JavaClientCodegen.JAVA8_MODE, true);
        properties.put(JavaJAXRSSpecServerCodegen.OPEN_API_SPEC_FILE_LOCATION, "openapi.yml");

        File output = Files.createTempDirectory("test").toFile();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("jaxrs-spec")
                .setAdditionalProperties(properties)
                .setInputSpec("src/test/resources/3_0/issue_1347.yaml")
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        DefaultGenerator generator = new DefaultGenerator(false);
        List<File> files = generator.opts(clientOptInput).generate();

        validateJavaSourceFiles(files);

        TestUtils.ensureContainsFile(files, output, "openapi.yml");
        TestUtils.ensureContainsFile(files, output, "src/gen/java/org/openapitools/api/DefaultApi.java");

        output.deleteOnExit();
    }

    @Test
    public void testGenerateApiWithCookieParameter_issue2908() throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put(JavaJAXRSSpecServerCodegen.OPEN_API_SPEC_FILE_LOCATION, "openapi.yml");

        File output = Files.createTempDirectory("test").toFile();

        final CodegenConfigurator configurator = new CodegenConfigurator()
                .setGeneratorName("jaxrs-spec")
                .setAdditionalProperties(properties)
                .setInputSpec("src/test/resources/3_0/issue_2908.yaml")
                .setOutputDir(output.getAbsolutePath().replace("\\", "/"));

        final ClientOptInput clientOptInput = configurator.toClientOptInput();
        DefaultGenerator generator = new DefaultGenerator(false);
        List<File> files = generator.opts(clientOptInput).generate();

        validateJavaSourceFiles(files);

        TestUtils.ensureContainsFile(files, output, "openapi.yml");
        files.stream().forEach(System.out::println);
        TestUtils.ensureContainsFile(files, output, "src/gen/java/org/openapitools/api/SomethingApi.java");
        TestUtils.assertFileContains(output.toPath().resolve("src/gen/java/org/openapitools/api/SomethingApi.java"), "@CookieParam");

        output.deleteOnExit();
    }

    @Test
    public void addsImportForSetArgument() throws IOException {
        File output = Files.createTempDirectory("test").toFile().getCanonicalFile();
        output.deleteOnExit();
        String outputPath = output.getAbsolutePath().replace('\\', '/');

        OpenAPI openAPI = new OpenAPIParser()
            .readLocation("src/test/resources/3_0/arrayParameter.yaml", null, new ParseOptions()).getOpenAPI();

        openAPI.getComponents().getParameters().get("operationsQueryParam").setSchema(new ArraySchema().uniqueItems(true));
        codegen.setOutputDir(output.getAbsolutePath());

        codegen.additionalProperties().put(CXFServerFeatures.LOAD_TEST_DATA_FROM_FILE, "true");

        ClientOptInput input = new ClientOptInput()
            .openAPI(openAPI)
            .config(codegen);

        DefaultGenerator generator = new DefaultGenerator(false);
        generator.opts(input).generate();

        Path path = Paths.get(outputPath + "/src/gen/java/org/openapitools/api/ExamplesApi.java");

        assertFileContains(path, "\nimport java.util.Set;\n");
    }

    @Test
    public void addsImportForSetResponse() throws IOException {
        File output = Files.createTempDirectory("test").toFile().getCanonicalFile();
        output.deleteOnExit();
        String outputPath = output.getAbsolutePath().replace('\\', '/');

        OpenAPI openAPI = new OpenAPIParser()
            .readLocation("src/test/resources/3_0/setResponse.yaml", null, new ParseOptions()).getOpenAPI();

        codegen.setOutputDir(output.getAbsolutePath());

        codegen.additionalProperties().put(CXFServerFeatures.LOAD_TEST_DATA_FROM_FILE, "true");

        ClientOptInput input = new ClientOptInput()
            .openAPI(openAPI)
            .config(codegen);

        DefaultGenerator generator = new DefaultGenerator();
        generator.opts(input).generate();

        Path path = Paths.get(outputPath + "/src/gen/java/org/openapitools/api/ExamplesApi.java");

        assertFileContains(path, "\nimport java.util.Set;\n");
    }

    @Test
    public void givenTwoOperationAndApiPerOperationTrueWhenAddOperationThenAdd2OperationsWithExpectedName() {
        String resourcePath = "/primaryresource";

        CodegenOperation getOperation = buildOperation(resourcePath, "GET");
        CodegenOperation putOperation = buildOperation(resourcePath, "PUT");

        codegen.setApiPerOperation(true);

        Operation operation = new Operation();
        Map<String, List<CodegenOperation>> operationList = new HashMap<>();

        codegen.addOperationToGroup("Primaryresource", resourcePath, operation, getOperation, operationList);
        codegen.addOperationToGroup("Primaryresource", resourcePath, operation, putOperation, operationList);

        Assert.assertEquals(operationList.size(), 2);
        Assert.assertTrue(operationList.containsKey("PrimaryresourceGet"));
        Assert.assertTrue(operationList.containsKey("PrimaryresourcePut"));
        Assert.assertEquals(getOperation.baseName, "PrimaryresourceGet");
        Assert.assertEquals(putOperation.baseName, "PrimaryresourcePut");
    }

    private CodegenOperation buildOperation(String resourcePath, String put) {
        CodegenOperation putOperation = new CodegenOperation();
        putOperation.httpMethod = put;
        putOperation.path = resourcePath;
        return putOperation;
    }

}
