# Publishing to Maven Central

This document describes how to publish JDatePicker to Maven Central.

## Prerequisites

### 1. Maven Central Account

1. Create an account at [https://central.sonatype.com](https://central.sonatype.com)
2. Generate a user token (this replaces the old username/password)
   - Go to your account settings
   - Generate a new token
   - Save the token username and password

### 2. GPG Key Setup

You need a GPG key to sign the artifacts:

```bash
# Generate a new GPG key (if you don't have one)
gpg --gen-key

# List your keys to get the key ID
gpg --list-secret-keys --keyid-format=long

# Export your private key (needed for GitHub Actions)
gpg --armor --export-secret-keys YOUR_KEY_ID > private-key.asc

# Publish your public key to a key server
gpg --keyserver keyserver.ubuntu.com --send-keys YOUR_KEY_ID
```

### 3. GitHub Secrets Configuration

Add the following secrets to your GitHub repository (Settings → Secrets and variables → Actions):

- `MAVEN_CENTRAL_USERNAME`: Your Maven Central token username
- `MAVEN_CENTRAL_TOKEN`: Your Maven Central token password
- `GPG_PRIVATE_KEY`: Your GPG private key (contents of private-key.asc)
- `GPG_PASSPHRASE`: Your GPG key passphrase

To add the GPG private key:
```bash
# Copy the entire contents of the private key file
cat private-key.asc | pbcopy  # macOS
cat private-key.asc | xclip -selection clipboard  # Linux
```

Then paste it into the GitHub secret value.

## Publishing Process

### Option 1: Manual Release (Recommended)

1. Ensure all changes are committed and pushed
2. Go to GitHub Actions → "Release to Maven Central"
3. Click "Run workflow"
4. Enter the release version (e.g., `2.0.0`)
5. Click "Run workflow"

The workflow will:
- Update the version in pom.xml
- Build and test the project
- Sign the artifacts with GPG
- Deploy to Maven Central
- Create a GitHub release and tag

### Option 2: Tag-based Release

Push a git tag starting with `v`:

```bash
# Update version in pom.xml manually first
cd library
mvn versions:set -DnewVersion=2.0.0
mvn versions:commit
cd ..

# Commit the version change
git add library/pom.xml
git commit -m "Release version 2.0.0"

# Create and push the tag
git tag v2.0.0
git push origin v2.0.0
```

### Option 3: Local Release

You can also release locally if needed:

```bash
cd library

# Set credentials in ~/.m2/settings.xml
# See Maven Central documentation for format

# Deploy to Maven Central
mvn clean deploy -Prelease
```

## Verifying the Release

1. Check Maven Central: https://central.sonatype.com/artifact/org.jdatepicker/jdatepicker
2. The artifact should appear within 15-30 minutes
3. It will sync to Maven Central search within a few hours

## Troubleshooting

### GPG Signing Issues

If you get GPG errors:
- Ensure GPG is installed: `gpg --version`
- Verify your key: `gpg --list-secret-keys`
- Test signing: `echo "test" | gpg --clearsign`

### Maven Central Deployment Issues

- Verify your credentials in GitHub secrets
- Check that all required metadata is in pom.xml:
  - name, description, url
  - licenses
  - developers
  - scm information
- Ensure sources and javadoc JARs are being generated

### Workflow Failures

Check the GitHub Actions logs for detailed error messages. Common issues:
- Missing or incorrect secrets
- GPG key format issues (ensure the entire key including headers is copied)
- Network issues (transient, can be retried)

## Release Checklist

Before publishing:

- [ ] All tests pass locally: `cd library && mvn clean verify`
- [ ] Version updated appropriately (follow semantic versioning)
- [ ] CHANGELOG or release notes prepared
- [ ] GitHub secrets are configured
- [ ] GPG key is valid and published to key server
- [ ] Maven Central account is set up and token generated

## First-Time Setup Summary

1. Create Maven Central account and generate token
2. Create/export GPG key
3. Add secrets to GitHub repository
4. Run the release workflow
5. Verify the release on Maven Central

## Additional Resources

- [Maven Central Publishing Guide](https://central.sonatype.org/publish/publish-guide/)
- [GPG Documentation](https://www.gnupg.org/documentation/)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
