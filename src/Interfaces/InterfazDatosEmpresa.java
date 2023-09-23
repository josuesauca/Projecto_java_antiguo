/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Modelo.ConfiguracionDatosEmpresa;

public interface InterfazDatosEmpresa {

    public ConfiguracionDatosEmpresa buscarDatos();

    public boolean modificarDatosEmpresa(ConfiguracionDatosEmpresa configuracionDatosEmpresa);

}
