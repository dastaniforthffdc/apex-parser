package com.nawforce.apexparser;

import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

public class ApexVisitorTest {

    static class TestVisitor extends ApexParserBaseVisitor<Integer> {
        public Integer methodCount = 0;

        @Override
        public Integer visitMethodDeclaration(ApexParser.MethodDeclarationContext ctx) {
            this.methodCount += 1;
            return 1 + super.visitChildren(ctx);
        }

        @Override
        public Integer defaultResult() {
            return 0;
        }
    }

    @Test
    void testVisitorIsVisited() throws IOException {
        ApexLexer lexer = new ApexLexer(new CaseInsensitiveInputStream(new StringReader(
                "public class Hello { public void func(){} }")));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ApexParser parser = new ApexParser(tokens);
        ApexParser.CompilationUnitContext context = parser.compilationUnit();

        TestVisitor visitor = new TestVisitor();
        visitor.visit(context);

        assert (1 == visitor.methodCount);
    }

}
