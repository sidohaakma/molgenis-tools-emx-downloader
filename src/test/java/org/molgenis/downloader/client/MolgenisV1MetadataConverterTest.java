package org.molgenis.downloader.client;

import org.molgenis.downloader.api.WriteableMetadataRepository;
import org.molgenis.downloader.api.metadata.Attribute;
import org.molgenis.downloader.api.metadata.Package;
import org.molgenis.downloader.api.metadata.Tag;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class MolgenisV1MetadataConverterTest
{
	private MolgenisV1MetadataConverter converter;
	private WriteableMetadataRepository metadataRepository;

	@BeforeClass
	public void setup()
	{
		metadataRepository = mock(WriteableMetadataRepository.class);
		converter = new MolgenisV1MetadataConverter(metadataRepository);
	}

	@Test
	public void toPackageTest()
	{
		when(metadataRepository.createPackage("name")).thenReturn(new Package("name"));
		when(metadataRepository.createPackage("parent")).thenReturn(new Package("parent"));
		when(metadataRepository.createTag("tag")).thenReturn(new Tag("tag"));

		Attribute nameAttr = new Attribute("fullName");
		Attribute descriptionAttr = new Attribute("description");
		Attribute parentAttr = new Attribute("parent");
		Attribute tagAttr = new Attribute("tags");

		String name = "name";
		String desc = "desc";
		String parent = "parent";
		String tag = "tag";

		Map<Attribute, String> map = new HashMap<>();
		map.put(nameAttr, name);
		map.put(descriptionAttr, desc);
		map.put(parentAttr, parent);
		map.put(tagAttr, tag);

		Package actual = converter.toPackage(map);
		Package expected = new Package("name");
		Package parentPackage = new Package("parent");
		Tag expectedTag = new Tag("tag");

		expected.setDescription("desc");
		expected.setName("name");
		expected.setParent(parentPackage);
		expected.addTag(expectedTag);

		assertEquals(actual, expected);
	}
}
