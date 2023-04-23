package com.malek.vols.controllers;

import java.text.ParseException;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import com.malek.vols.entities.Vol;
import com.malek.vols.service.VolService;

import jakarta.validation.Valid;



@Controller
public class VolController {
@Autowired
VolService volService;

@RequestMapping("/showCreate")
public String showCreate(ModelMap modelMap)
{
modelMap.addAttribute("vol", new Vol());

return "formVol";
}

@RequestMapping("/saveVol")
public String saveVol(@Valid Vol vol, BindingResult bindingResult)
{
if (bindingResult.hasErrors()) return "formVol";
 
volService.saveVol(vol);
return "formVol";
}

@RequestMapping("/ListeVols")
public String listeVols(ModelMap modelMap,
@RequestParam (name="page",defaultValue = "0") int page,
@RequestParam (name="size", defaultValue = "2") int size)
{
Page<Vol> vo = volService.getAllVolsParPage(page, size);
modelMap.addAttribute("vols", vo);
 modelMap.addAttribute("pages", new int[vo.getTotalPages()]);
modelMap.addAttribute("currentPage", page);
return "listeVols";
}
@RequestMapping("/supprimerVol")
public String supprimerVol(@RequestParam("id") Long id,ModelMap modelMap,@RequestParam (name="page",defaultValue = "0") int page,@RequestParam (name="size", defaultValue = "2") int size)
{
volService.deleteVolById(id);
Page<Vol> vo = volService.getAllVolsParPage(page, size);
modelMap.addAttribute("vols", vo);
modelMap.addAttribute("pages", new int[vo.getTotalPages()]);
if (size/2 == 1)
{modelMap.addAttribute("currentPage", page-1);
modelMap.addAttribute("size", size);}
else {
	modelMap.addAttribute("currentPage", page);
	modelMap.addAttribute("size", size);
}
return "listeVols";
}
@RequestMapping("/modifierVol")
public String editerVol(@RequestParam("id") Long id,ModelMap modelMap)
{
Vol v= volService.getVol(id);
modelMap.addAttribute("vol", v);
return "editerVol";
}
@RequestMapping("/updateVol")
public String updateVol(@ModelAttribute("vol") Vol vol,
@RequestParam("date") String date,
ModelMap modelMap) throws ParseException 
{
 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
 Date dateDepart = dateformat.parse(String.valueOf(date));
 vol.setDateDepart(dateDepart);
 
 volService.updateVol(vol);
 List<Vol> vo = volService.getAllVols();
 modelMap.addAttribute("vols", vo);
return "listeVols";
}

}
	

