package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.api.dto.PrestamoRequest;
import com.ejemplo.demo.api.dto.PrestamoResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
public class PrestamoService {

    public PrestamoResponse simular(PrestamoRequest request) {

        BigDecimal P = request.monto();

        BigDecimal r = request.tasaAnual()
                .divide(BigDecimal.valueOf(12 * 100), MathContext.DECIMAL64);

        int n = request.meses();

        BigDecimal unoMasR = BigDecimal.ONE.add(r);
        BigDecimal potencia = unoMasR.pow(n);

        BigDecimal cuota = P.multiply(r.multiply(potencia))
                .divide(potencia.subtract(BigDecimal.ONE), MathContext.DECIMAL64);

        BigDecimal totalPagar = cuota.multiply(BigDecimal.valueOf(n));
        BigDecimal interesTotal = totalPagar.subtract(P);

        return new PrestamoResponse(cuota, interesTotal, totalPagar);
    }
}